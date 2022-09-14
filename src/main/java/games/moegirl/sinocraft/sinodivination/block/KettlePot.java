package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.api.block.AbstractEntityBlock;
import games.moegirl.sinocraft.sinodivination.blockentity.KettlePotEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.data.SDTags;
import games.moegirl.sinocraft.sinodivination.util.container.InputOnlyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Random;

public class KettlePot extends AbstractEntityBlock<KettlePotEntity> {

    public static final VoxelShape SHAPE = box(1, 0, 1, 14, 7, 14);
    public static final VoxelShape SHAPE_INTERACTION = box(1, 0, 1, 14, 9, 14);

    public KettlePot() {
        super(SDBlockEntities.KETTLE_POT);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        SDBlockEntities.getKettlePot(pLevel, pPos).ifPresent(entity -> {
            if (pPlayer.isShiftKeyDown()) {
                InputOnlyContainer input = (InputOnlyContainer) entity.getInput();
                for (int i = 0; i < input.getSlots(); i++) {
                    ItemStack stack = input.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        ItemHandlerHelper.giveItemToPlayer(pPlayer, stack);
                        input.setStackInSlot2(i, ItemStack.EMPTY);
                        break;
                    }
                }
            } else if (entity.isReady()) {
                entity.run();
            } else if (FluidUtil.getFluidHandler(pPlayer.getItemInHand(pHand)).isPresent()) {
                ItemStack stack = pPlayer.getItemInHand(pHand);
                // fluid
                FluidTank tank = entity.getTank();
                FluidActionResult result = FluidUtil.tryFillContainer(stack, tank, Integer.MAX_VALUE, pPlayer, true);
                if (result.isSuccess()) {
                    stack.shrink(1);
                    pPlayer.addItem(result.getResult());
                } else {
                    FluidActionResult r = FluidUtil.tryEmptyContainer(stack, tank, Integer.MAX_VALUE, pPlayer, true);
                    if (r.isSuccess()) {
                        stack.shrink(1);
                        pPlayer.addItem(result.getResult());
                    } else {
                        entity.takeResult(pPlayer.getItemInHand(pHand))
                                .ifPresent(ri -> ItemHandlerHelper.giveItemToPlayer(pPlayer, ri));
                    }
                }
            } else {
                entity.takeResult(pPlayer.getItemInHand(pHand))
                        .ifPresent(result -> ItemHandlerHelper.giveItemToPlayer(pPlayer, result));
            }
        });
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
        SDBlockEntities.getKettlePot(pLevel, pPos)
                .flatMap(KettlePotEntity::getRecipe)
                .ifPresent(__ -> {
                    if (pLevel.getBlockState(pPos.below()).is(SDTags.HEAT_SOURCE)) {
                        // todo by lq2007: spawn burning particle
                    } else {
                        // todo by lq2007: spawn cooldown particle
                    }
                });
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return SHAPE_INTERACTION;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        SDBlockEntities.getKettlePot(level, pos).ifPresent(pot -> {
            if (entity instanceof ItemEntity ie) {
                ItemStack item = ie.getItem();
                ItemStack remain = ItemHandlerHelper.insertItem(pot.getInput(), item, false);
                if (remain.isEmpty()) {
                    ie.remove(Entity.RemovalReason.KILLED);
                    pot.setChanged();
                } else if (remain.getCount() < item.getCount()) {
                    ie.setItem(remain);
                    pot.setChanged();
                }
            }
        });
    }
}
