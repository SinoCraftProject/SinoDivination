package games.moegirl.sinocraft.sinodivination.block.base;

import games.moegirl.sinocraft.sinocore.api.block.AbstractEntityBlock;
import games.moegirl.sinocraft.sinodivination.blockentity.IAltarEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class AltarBlock<T extends BlockEntity & IAltarEntity> extends AbstractEntityBlock<T> {

    public AltarBlock(Supplier<BlockEntityType<T>> entityType) {
        super(entityType);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            ItemStack itemInHand = pPlayer.getItemInHand(pHand);
            pLevel.getBlockEntity(pPos, entityType.get()).ifPresent(entity -> {
                if (itemInHand.isEmpty() || pPlayer.isShiftKeyDown()) {
                    ItemStack item = entity.takeItem();
                    if (!item.isEmpty()) {
                        if (!pPlayer.getInventory().add(item)) {
                            pPlayer.drop(item, false);
                        }
                    }
                } else {
                    ItemStack stack = entity.putItem(itemInHand);
                    pPlayer.setItemInHand(pHand, stack);
                }
            });
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }
}
