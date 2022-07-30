package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinodivination.menu.CarvingTableMenu;
import games.moegirl.sinocraft.sinodivination.menu.SDMenus;
import games.moegirl.sinocraft.sinodivination.data.SDLangKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CarvingTable extends Block {

    public static final TranslatableComponent TITLE = new TranslatableComponent(SDLangKeys.CARVING_TABLE_TITLE);

    public CarvingTable() {
        super(Properties.copy(Blocks.CRAFTING_TABLE));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            pPlayer.openMenu(getMenuProvider(pState, pLevel, pPos));
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((i, arg, arg2) -> {
            CarvingTableMenu menu = SDMenus.CARVING_TABLE.get().create(i, arg);
            menu.pos = pos;
            return menu;
        }, TITLE);
    }
}
