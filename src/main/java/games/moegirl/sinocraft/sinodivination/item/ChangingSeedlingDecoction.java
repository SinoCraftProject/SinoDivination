package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ChangingSeedlingDecoction extends Item {

    private static final List<Function<BlockState, Optional<BlockState>>> RECIPES = new ArrayList<>();

    public static void registerRecipe(Predicate<? super BlockState> from, Supplier<BlockState> to) {
        RECIPES.add(bs -> Optional.of(bs).filter(from).map(b -> to.get()));
    }

    public static void registerRecipe(Function<BlockState, Optional<BlockState>> recipe) {
        RECIPES.add(recipe);
    }

    public ChangingSeedlingDecoction() {
        super(new Properties().tab(DivinationTab.INSTANCE).craftRemainder(Items.BOWL));
        registerRecipe(b -> b.getBlock() == Blocks.BIRCH_SAPLING,
                () -> SDTrees.COTINUS.getBlocks().sapling().defaultBlockState());
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide) {
            BlockPos pos = pContext.getClickedPos();
            Level world = pContext.getLevel();
            BlockState bs = world.getBlockState(pos);
            Optional<BlockState> result = RECIPES.stream()
                    .map(f -> f.apply(bs))
                    .filter(Optional::isPresent)
                    .flatMap(Optional::stream)
                    .findFirst();
            if (result.isPresent()) {
                world.setBlock(pos, result.get(), 2);
                Player player = pContext.getPlayer();
                if (player != null) {
                    player.setItemInHand(pContext.getHand(), new ItemStack(Items.BOWL));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(pContext);
    }
}
