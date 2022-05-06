package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinodivination.block.GlutinousRise;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GlutinousRiseSeed extends ItemNameBlockItem {

    public GlutinousRiseSeed() {
        super(SDBlocks.GLUTINOUS_RISE.get(), new Properties().tab(DivinationTab.INSTANCE));
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        GlutinousRise block = SDBlocks.GLUTINOUS_RISE.get();
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        BlockState state = level.getBlockState(pos);
        ItemStack stack = pContext.getItemInHand();
        if (block.mayPlaceOn(state, level, pos)) {
            BlockState rise = block.defaultBlockState();
            if (level.setBlock(pContext.getClickedPos(), rise, 11)) {
                block.setPlacedBy(level, pos, rise, pContext.getPlayer(), stack);
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
