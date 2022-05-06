package games.moegirl.sinocraft.sinodivination.util;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.Supplier;

public class Lootables {

    public static LootItemCondition.Builder isGrown(CropBlock crop) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(crop)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(crop.getAgeProperty(), crop.getMaxAge()));
    }

    public static LootPoolSingletonContainer.Builder<?> item(ItemLike item) {
        return LootItem.lootTableItem(item);
    }

    public static LootPoolSingletonContainer.Builder<?> item(Supplier<? extends ItemLike> item) {
        return LootItem.lootTableItem(item.get());
    }

    public static LootPoolSingletonContainer.Builder<?> item(ItemLike item, int count) {
        return LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(ConstantValue.exactly(count)));
    }

    public static LootPoolSingletonContainer.Builder<?> item(Supplier<? extends ItemLike> item, int count) {
        return LootItem.lootTableItem(item.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(count)));
    }

    public static LootPoolSingletonContainer.Builder<?> item(ItemLike item, int min, int max) {
        return LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)));
    }

    public static LootPoolSingletonContainer.Builder<?> item(Supplier<? extends ItemLike> item, int min, int max) {
        return LootItem.lootTableItem(item.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)));
    }
}
