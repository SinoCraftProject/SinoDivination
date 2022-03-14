package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.api.tree.Tree;
import games.moegirl.sinocraft.sinodivination.blockentity.CotinusFenceGateBEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.CotinusTrapdoorBEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;

import javax.annotation.Nullable;

public class CotinusFenceGateBlock extends FenceGateBlock implements EntityBlock {

    public CotinusFenceGateBlock(Tree tree) {
        super(Properties.copy(Blocks.OAK_FENCE_GATE));
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        CotinusFenceGateBEntity blockEntity = (CotinusFenceGateBEntity) pLevel.getBlockEntity(pPos);
        assert blockEntity != null;
        blockEntity.getChecker().setOwner(pPlacer);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CotinusFenceGateBEntity(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> GameEventListener getListener(Level pLevel, T pBlockEntity) {
        return (CotinusFenceGateBEntity) pBlockEntity;
    }
}
