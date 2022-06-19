package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.api.block.AbstractEntityBlock;
import games.moegirl.sinocraft.sinodivination.blockentity.KettlePotEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.util.SDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Random;

public class KettlePot extends AbstractEntityBlock<KettlePotEntity> {

    public static final VoxelShape SHAPE = box(1, 0, 1, 14, 7, 14);

    public KettlePot() {
        super(SDBlockEntities.KETTLE_POT);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            pLevel.getBlockEntity(pPos, SDBlockEntities.KETTLE_POT.get()).ifPresent(entity -> {
                ItemStack stack = pPlayer.getItemInHand(pHand);
                if (stack.isEmpty() || pPlayer.isShiftKeyDown()) {
                    InvWrapper output = entity.getOutput();
                    if (!output.getStackInSlot(0).isEmpty()) {
                        // take item
                        ItemStack take = output.extractItem(0, Integer.MAX_VALUE, false);
                        ItemHandlerHelper.giveItemToPlayer(pPlayer, take);
                    } else {
                        // start
                        entity.beginRecipe();
                    }
                } else {
                    if (FluidUtil.getFluidHandler(stack).isPresent()) {
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
                            }
                        }
                    } else {
                        InvWrapper input = entity.getInput();
// todo by lq2007: stop here
                    }
                }
            });
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
        if (pLevel.isClientSide && pLevel.isLoaded(pPos)) {
            pLevel.getBlockEntity(pPos, SDBlockEntities.KETTLE_POT.get())
                    .flatMap(KettlePotEntity::getRunningRecipe)
                    .ifPresent(__ -> {
                        if (pLevel.getBlockState(pPos.below()).is(SDTags.HEAT_SOURCE)) {
                            // todo by lq2007: spawn burning particle
                        } else {
                            // todo by lq2007: spawn cooldown particle
                        }
                    });
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
