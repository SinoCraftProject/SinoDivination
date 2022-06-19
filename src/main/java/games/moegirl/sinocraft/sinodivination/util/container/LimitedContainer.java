package games.moegirl.sinocraft.sinodivination.util.container;

import games.moegirl.sinocraft.sinocore.api.utility.Self;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

public class LimitedContainer<T extends LimitedContainer<T>> extends InvWrapper implements Self<T>, INBTSerializable<ListTag> {

    protected SlotChecker checker = (_1, _2) -> true;
    protected final ContainerWrapper container;
    protected boolean saveEmpty = false;

    public LimitedContainer(Container inv) {
        super(new ContainerWrapper(inv));
        container = (ContainerWrapper) super.getInv();
    }

    public LimitedContainer(int count) {
        this(new SimpleContainer(count));
    }

    public LimitedContainer(int count, int stackSize) {
        this(new SimpleContainer(count) {
            final int s = Math.min(64, stackSize);

            @Override
            public int getMaxStackSize() {
                return s;
            }
        });
    }

    public T setChecker(SlotChecker checker) {
        this.checker = checker;
        return self();
    }

    public T addChecker(SlotChecker checker) {
        this.checker = SlotChecker.and(checker, this.checker);
        return self();
    }

    public T addChangeListener(ContainerListener listener) {
        container.addListener(listener);
        return self();
    }

    public T bindEntityChange(BlockEntity entity) {
        container.addListener(__ -> entity.setChanged());
        return self();
    }

    public T saveEmpty() {
        saveEmpty = true;
        return self();
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return isItemValid(slot, stack) ? super.insertItem(slot, stack, simulate) : stack;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return checker.check(slot, stack) && super.isItemValid(slot, stack);
    }

    public ItemStack insertItem2(int slot, ItemStack stack, boolean simulate) {
        if (getRawInv().getClass() == SimpleContainer.class) {
            return super.insertItem(slot, stack, simulate);
        }

        if (stack.isEmpty())
            return ItemStack.EMPTY;

        ItemStack stackInSlot = getInv().getItem(slot);

        int m;
        if (!stackInSlot.isEmpty()) {
            if (stackInSlot.getCount() >= Math.min(stackInSlot.getMaxStackSize(), getSlotLimit(slot)))
                return stack;

            if (!ItemHandlerHelper.canItemStacksStack(stack, stackInSlot))
                return stack;

            m = Math.min(stack.getMaxStackSize(), getSlotLimit(slot)) - stackInSlot.getCount();

            if (stack.getCount() <= m) {
                if (!simulate) {
                    ItemStack copy = stack.copy();
                    copy.grow(stackInSlot.getCount());
                    getInv().setItem(slot, copy);
                    getInv().setChanged();
                }

                return ItemStack.EMPTY;
            } else {
                // copy the stack to not modify the original one
                stack = stack.copy();
                if (!simulate) {
                    ItemStack copy = stack.split(m);
                    copy.grow(stackInSlot.getCount());
                    getInv().setItem(slot, copy);
                    getInv().setChanged();
                } else {
                    stack.shrink(m);
                }
                return stack;
            }
        } else {
            m = Math.min(stack.getMaxStackSize(), getSlotLimit(slot));
            if (m < stack.getCount()) {
                // copy the stack to not modify the original one
                stack = stack.copy();
                if (!simulate) {
                    getInv().setItem(slot, stack.split(m));
                    getInv().setChanged();
                } else {
                    stack.shrink(m);
                }
                return stack;
            } else {
                if (!simulate) {
                    getInv().setItem(slot, stack);
                    getInv().setChanged();
                }
                return ItemStack.EMPTY;
            }
        }
    }

    @NotNull
    public ItemStack extractItem2(int slot, int amount, boolean simulate) {
        return super.extractItem(slot, amount, simulate);
    }

    public boolean isItemValid2(int slot, @NotNull ItemStack stack) {
        return super.isItemValid(slot, stack);
    }

    public Container getRawInv() {
        return container.container;
    }

    @Override
    public ContainerWrapper getInv() {
        return container;
    }

    @Override
    public ListTag serializeNBT() {
        ListTag list = new ListTag();
        for (int i = 0; i < getSlots(); i++) {
            ItemStack stack = getStackInSlot(i);
            if (stack.isEmpty() && !saveEmpty) {
                continue;
            }
            CompoundTag nbt = new CompoundTag();
            nbt.putByte("Slot", (byte)i);
            stack.save(nbt);
            list.add(nbt);
        }
        return list;
    }

    @Override
    public void deserializeNBT(ListTag nbt) {
        container.container.clearContent();
        for (Tag tag : nbt) {
            CompoundTag t = (CompoundTag) tag;
            int slot = t.getByte("Slot");
            setStackInSlot(slot, ItemStack.of(t));
        }
    }

    public void save(CompoundTag tag, String name) {
        tag.put(name, serializeNBT());
    }

    public void load(CompoundTag nbt, String name) {
        deserializeNBT(nbt.getList(name, Tag.TAG_COMPOUND));
    }
}