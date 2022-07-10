package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.menu.SilkwormPlaqueMenu;
import games.moegirl.sinocraft.sinodivination.util.SDLangKeys;
import games.moegirl.sinocraft.sinodivination.util.NbtUtils;
import games.moegirl.sinocraft.sinodivination.util.container.ComposeItemHandler;
import games.moegirl.sinocraft.sinodivination.util.container.InputOnlyContainer;
import games.moegirl.sinocraft.sinodivination.util.container.OutputOnlyContainer;
import games.moegirl.sinocraft.sinodivination.util.container.SlotChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SilkwormPlaqueEntity extends BlockEntity implements MenuProvider, BlockEntityTicker<SilkwormPlaqueEntity> {

    public static final int SILKWORM_COUNT = 9;

    private final InputOnlyContainer in = new InputOnlyContainer(SILKWORM_COUNT)
            .bindEntityChange(this)
            .setChecker(SlotChecker.of(SDItems.SILKWORM_BABY));
    private final OutputOnlyContainer out = new OutputOnlyContainer(SILKWORM_COUNT).bindEntityChange(this);
    private final InputOnlyContainer feed = new InputOnlyContainer(1)
            .bindEntityChange(this)
            .setChecker(SlotChecker.of(ItemTags.LEAVES));
    private final SilkwormHolder[] silkworms = new SilkwormHolder[SILKWORM_COUNT];
    private float nutrition = 0;

    private final IItemHandler handler = new ComposeItemHandler().append(in).append(out).append(feed);

    public SilkwormPlaqueEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
        for (int i = 0; i < silkworms.length; i++) {
            silkworms[i] = new SilkwormHolder(i);
        }
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent(SDLangKeys.SILKWORM_PLAGUE_TITLE);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new SilkwormPlaqueMenu(pContainerId, pInventory, this);
    }

    public InvWrapper in() {
        return in;
    }

    public InvWrapper out() {
        return out;
    }

    public InvWrapper feed() {
        return feed;
    }

    public SilkwormHolder silkworm(int index) {
        return silkworms[index];
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, SilkwormPlaqueEntity pBlockEntity) {
        if (!pLevel.isClientSide) {
            if (nutrition <= 90) {
                nutrition += feed.extractItem2(0, (int) ((100 - nutrition) / 10), false).getCount() * 10;
            }
            for (SilkwormHolder silkworm : silkworms) {
                int i = silkworm.index;
                if (silkworm.cached > 0) {
                    silkworm.cached = out.insertItem2(i, new ItemStack(SDItems.SILK.get(), silkworm.cached), false).getCount();
                }

                if (silkworm.cooldown > 0) {
                    silkworm.cooldown--;
                    continue;
                }

                if (silkworm.isBlocking()) {
                    continue;
                }

                if (in.getStackInSlot(i).isEmpty()) {
                    silkworm.progress = 0;
                } else if (nutrition > 0) {
                    silkworm.progress++;
                    nutrition--;
                    if (silkworm.progress == 100) {
                        silkworm.cached += out.insertItem2(i, new ItemStack(SDItems.SILK.get()), false).getCount();
                    }
                    silkworm.cooldown = 10;
                }
            }
            setChanged();
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        in.load(pTag, "in");
        out.load(pTag, "out");
        feed.load(pTag, "feed");
        NbtUtils.loadList(pTag, "silkworm", silkworms);
        nutrition = pTag.getFloat("nutrition");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        in.save(pTag, "in");
        out.save(pTag, "out");
        feed.save(pTag, "feed");
        NbtUtils.saveList(pTag, "silkworm", silkworms);
        pTag.putFloat("nutrition", nutrition);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == cap) {
            return LazyOptional.of(() -> handler).cast();
        }
        return super.getCapability(cap, side);
    }

    public static class SilkwormHolder implements INBTSerializable<CompoundTag> {
        final int index;
        int progress = 0;
        int cooldown = 0;
        int cached = 0;

        SilkwormHolder(int index) {
            this.index = index;
        }

        public float progress() {
            return cached == 0 ? ((float) progress / 100) : 1;
        }

        public boolean isBlocking() {
            return cached > 0;
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putByte("slot", (byte) index);
            nbt.putByte("progress", (byte) progress);
            nbt.putByte("cooldown", (byte) cooldown);
            nbt.putInt("cached", cached);
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            progress = nbt.getByte("progress");
            cooldown = nbt.getByte("cooldown");
            cached = nbt.getInt("cached");
        }
    }
}
