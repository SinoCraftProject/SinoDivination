package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.api.block.BlockTreeLeaves;
import games.moegirl.sinocraft.sinocore.api.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.api.tree.Tree;
import games.moegirl.sinocraft.sinocore.api.utility.BlockLootables;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class JujubeLeaves extends BlockTreeLeaves implements ILootableBlock {

    public JujubeLeaves(Tree tree) {
        super(tree);
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        LootItemCondition.Builder fortune = BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F);
        LootPoolSingletonContainer.Builder<?> stick = LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)));
        LootPoolSingletonContainer.Builder<?> jujube = LootItem.lootTableItem(SDItems.JUJUBE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)));
        return helper.createSilkTouchOrShearsDispatchTable(this, ((LootPoolSingletonContainer.Builder<?>) helper.applyExplosionCondition(false, LootItem.lootTableItem(getTree().sapling())))
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, BlockLootables.NORMAL_LEAVES_SAPLING_CHANCES)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(BlockLootables.HAS_NO_SHEARS_OR_SILK_TOUCH)
                        .add(((LootPoolSingletonContainer.Builder<?>) helper.applyExplosionDecay(false, stick)).when(fortune)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(BlockLootables.HAS_NO_SHEARS_OR_SILK_TOUCH)
                        .add(((LootPoolSingletonContainer.Builder<?>) helper.applyExplosionDecay(false, jujube)).when(fortune)));
    }
}
