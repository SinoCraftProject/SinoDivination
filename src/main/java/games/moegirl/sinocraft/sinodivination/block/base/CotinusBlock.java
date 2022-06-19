package games.moegirl.sinocraft.sinodivination.block.base;

import games.moegirl.sinocraft.sinocore.api.woodwork.IWoodwork;
import games.moegirl.sinocraft.sinocore.api.woodwork.Woodwork;
import games.moegirl.sinocraft.sinodivination.blockentity.ICotinusEntity;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.gameevent.GameEventListener;

import javax.annotation.Nullable;

public interface CotinusBlock extends EntityBlock, IWoodwork {

    default void placedBy(Level pLevel, BlockPos pPos, @Nullable LivingEntity pPlacer) {
        if (pPlacer != null) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ICotinusEntity ce) {
                ce.owner().setOwner(pPlacer);
            }
        }
    }

    @Override
    default <T2 extends BlockEntity> GameEventListener getListener(Level pLevel, T2 pBlockEntity) {
        return (GameEventListener) pBlockEntity;
    }

    @Override
    default Woodwork getWoodwork() {
        return SDWoodwork.COTINUS;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    default boolean isAllowed(BlockPos pPos, @Nullable Entity pEntity) {
        return isAllowed(pPos, pEntity, true);
    }

    default boolean isAllowed(BlockPos pPos, @Nullable Entity pEntity, boolean allowedEmptyEntity) {
        if (pEntity == null) return allowedEmptyEntity;
        return pEntity.level.getBlockEntity(pPos) instanceof ICotinusEntity entity && entity.owner().isAllowed(pEntity);
    }
}
