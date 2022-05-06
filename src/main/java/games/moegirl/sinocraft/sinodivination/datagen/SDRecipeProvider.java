package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.block.WoodenChest;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class SDRecipeProvider extends RecipeProvider {

    public SDRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        SDTrees.COTINUS.register().addRecipes(pFinishedRecipeConsumer, SDWoodwork.COTINUS.planks());
        SDTrees.SOPHORA.register().addRecipes(pFinishedRecipeConsumer, SDWoodwork.SOPHORA.planks());
        SDTrees.JUJUBE.register().addRecipes(pFinishedRecipeConsumer, SDWoodwork.JUJUBE.planks());
        SDWoodwork.COTINUS.register().addRecipes(pFinishedRecipeConsumer, null);
        SDWoodwork.SOPHORA.register().addRecipes(pFinishedRecipeConsumer, null);
        SDWoodwork.JUJUBE.register().addRecipes(pFinishedRecipeConsumer, null);
        addChest(SDBlocks.COTINUS_CHEST, pFinishedRecipeConsumer);
        addChest(SDBlocks.JUJUBE_CHEST, pFinishedRecipeConsumer);
        addChest(SDBlocks.SOPHORA_CHEST, pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(SDItems.GHOST_KILLING_STICK.get())
                .group(SinoDivination.MOD_ID)
                .pattern("X")
                .pattern("Y")
                .pattern("X")
                .define('X', SDTrees.COTINUS.wood())
                .define('Y', Items.STICK)
                .unlockedBy("has_block", has(SDTrees.COTINUS.wood()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(SDItems.DIVINE_POWER_STICK.get())
                .group(SinoDivination.MOD_ID)
                .pattern("X")
                .pattern("Y")
                .pattern("X")
                .define('X', SDTrees.JUJUBE.wood())
                .define('Y', Items.STICK)
                .unlockedBy("has_block", has(SDTrees.JUJUBE.wood()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(SDItems.GHOST_GUARD_STICK.get())
                .group(SinoDivination.MOD_ID)
                .pattern("X")
                .pattern("Y")
                .pattern("X")
                .define('X', SDTrees.SOPHORA.wood())
                .define('Y', Items.STICK)
                .unlockedBy("has_block", has(SDTrees.SOPHORA.wood()))
                .save(pFinishedRecipeConsumer);
    }

    private <T extends WoodenChest> void addChest(RegistryObject<T> chestObj, Consumer<FinishedRecipe> consumer) {
        T chest = chestObj.get();
        ShapedRecipeBuilder.shaped(chest).group("chest")
                .define('#', chest.planks())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_planks", InventoryChangeTrigger.TriggerInstance.hasItems(chest.planks()))
                .save(consumer);
    }
}
