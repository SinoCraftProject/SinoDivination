package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.api.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.api.utility.BlockLootables;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.util.CrossBlocks;
import games.moegirl.sinocraft.sinodivination.util.Lootables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

public class GlutinousRise extends CropBlock implements ILootableBlock {

    public GlutinousRise() {
        super(Properties.copy(Blocks.CARROTS));
    }

    @Override
    public boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        CrossBlocks blocks = new CrossBlocks(pLevel, pPos.above());
        return pState.is(BlockTags.DIRT)
                && blocks.is(Direction.UP, Blocks.WATER)
                && blocks.isSource(Direction.UP)
                && blocks.isDry(Direction.UP, 2);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return SDItems.GLUTINOUS_RISE_SEED.get();
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        return helper.applyExplosionDecay(false, new LootTable.Builder()
                .withPool(new LootPool.Builder().add(Lootables.item(this)))
                .withPool(new LootPool.Builder()
                        .when(Lootables.isGrown(this))
                        .add(Lootables.item(getBaseSeedId(), 0, 1))
                        .add(Lootables.item(SDItems.GLUTINOUS_RISE.get(), 3, 4))));
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return Fluids.WATER.getSource(false);
    }
}
