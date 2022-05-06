package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.api.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.api.utility.BlockLootables;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class Garlic extends CropBlock implements ILootableBlock {

    public Garlic() {
        super(Properties.copy(Blocks.CARROTS));
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return SDItems.GARLIC_SEED.get();
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        ItemLike crop = SDItems.GARLIC.get();
        ItemLike seed = getBaseSeedId();
        LootItemCondition.Builder isGrown = LootItemBlockStatePropertyCondition.hasBlockStateProperties(this)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(getAgeProperty(), getMaxAge()));
        return helper.applyExplosionDecay(false, LootTable.lootTable()
                // 1 seed
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(seed)))
                // grown: [0-1] seed, [2, 3] crop
                .withPool(LootPool.lootPool().when(isGrown)
                        .add(LootItem.lootTableItem(seed).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))))
                        .add(LootItem.lootTableItem(crop).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 3))))));
    }
}
