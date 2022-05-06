package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class SophoraFenceGate extends FenceGateBlock implements ISophoraBlock {

    public SophoraFenceGate(Properties properties) {
        super(properties);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        placedBy(pLevel, pPos, pPlacer);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SDBlockEntities.SOPHORA_ENTITY.get().create(pPos, pState);
    }
}
