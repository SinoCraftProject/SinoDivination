package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.util.container.InputOnlyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class AltarEntity extends BlockEntity implements IAltarEntity {

    private final InputOnlyContainer in = new InputOnlyContainer(1, 1).bindEntityChange(this);
    @Nullable
    private Direction direction = null;

    public AltarEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> in).cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public ItemStack takeItem() {
        ItemStack stack = in.getStackInSlot(0);
        in.setStackInSlot(0, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public ItemStack putItem(ItemStack stack) {
        return in.insertItem2(0, stack, false);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Optional<Direction> getDirection() {
        return isRemoved() ? Optional.empty() : Optional.ofNullable(direction);
    }
}
