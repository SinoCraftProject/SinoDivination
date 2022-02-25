package games.moegirl.sinocraft.sinodivination.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class PotBlockEntity extends BlockEntity {
    private FluidTank tank = new FluidTank(1000);

    public PotBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntity.POT_BLOCK_ENTITY.get(), pos, blockState);
    }

    /**
     * @param level the current world
     * @param pos   the position of the block
     * @param state the state of the block
     * @param te    the block entity
     */
    public static void tick(Level level, BlockPos pos, BlockState state, PotBlockEntity te) {
        if (te.getLevel() == null) {
            return;
        }
        if (te.getLevel().getBlockState(pos).getBlock() != state.getBlock()) {
            te.getLevel().removeBlockEntity(pos);
        }

        if (level.getBlockState(pos.below()).getLightBlock(level, pos.below()) > 15 && !te.tank.isEmpty()) {

        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        tank.readFromNBT(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tank.writeToNBT(tag);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return super.getCapability(cap);
    }
}
