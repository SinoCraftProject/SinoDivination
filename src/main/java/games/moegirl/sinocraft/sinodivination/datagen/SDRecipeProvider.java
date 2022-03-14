package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class SDRecipeProvider extends RecipeProvider {

    public SDRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        SDTrees.REGISTER.addRecipes(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(SDItems.GHOST_KILLING_STICK.get())
                .group(SinoDivination.MOD_ID)
                .pattern("X").pattern("Y").pattern("X")
                .define('X', SDTrees.COTINUS.getBlocks().wood())
                .define('Y', Items.STICK)
                .unlockedBy("has_block", has(SDTrees.COTINUS.getBlocks().wood()))
                .save(pFinishedRecipeConsumer);
    }
}
