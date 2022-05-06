package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.api.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.api.utility.BlockLootables;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.util.Lootables;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ArgyWormwood extends CropBlock implements ILootableBlock {

    private static final VoxelShape SHAPE = box(0, 0, 0, 16, 32, 16);

    public ArgyWormwood() {
        super(Properties.copy(Blocks.CARROTS));
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return SDItems.ARGY_WORMWOOD_SEED.get();
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        return helper.applyExplosionDecay(false, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(Lootables.item(getBaseSeedId())))
                .withPool(LootPool.lootPool().when(Lootables.isGrown(this))
                        .add(Lootables.item(getBaseSeedId(), 0, 1))
                        .add(Lootables.item(SDItems.ARGY_WORMWOOD_LEAVES, 2))));
    }
}
