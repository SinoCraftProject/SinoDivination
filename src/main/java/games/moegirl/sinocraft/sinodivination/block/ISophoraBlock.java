package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.api.woodwork.IWoodwork;
import games.moegirl.sinocraft.sinocore.api.woodwork.Woodwork;
import games.moegirl.sinocraft.sinodivination.blockentity.ISophoraEntity;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.gameevent.GameEventListener;

import javax.annotation.Nullable;

public interface ISophoraBlock extends EntityBlock, IWoodwork {

    default void placedBy(Level pLevel, BlockPos pPos, @Nullable LivingEntity pPlacer) {
        if (pPlacer != null) {
            ISophoraEntity blockEntity = (ISophoraEntity) pLevel.getBlockEntity(pPos);
            assert blockEntity != null;
            blockEntity.setEntity(pPlacer);
        }
    }

    @Override
    default <T2 extends BlockEntity> GameEventListener getListener(Level pLevel, T2 pBlockEntity) {
        return (GameEventListener) pBlockEntity;
    }

    @Override
    default Woodwork getWoodwork() {
        return SDWoodwork.SOPHORA;
    }
}
