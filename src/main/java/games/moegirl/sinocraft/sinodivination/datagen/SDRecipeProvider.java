package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinocore.api.tree.Tree;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.block.base.WoodenChest;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.recipe.ChangeSoupRecipe;
import games.moegirl.sinocraft.sinodivination.recipe.KettlePotRecipe;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class SDRecipeProvider extends RecipeProvider {

    public SDRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        SDTrees.COTINUS.register().addRecipes(consumer, SDWoodwork.COTINUS.planks());
        SDTrees.SOPHORA.register().addRecipes(consumer, SDWoodwork.SOPHORA.planks());
        SDTrees.JUJUBE.register().addRecipes(consumer, SDWoodwork.JUJUBE.planks());
        SDWoodwork.COTINUS.register().addRecipes(consumer, null);
        SDWoodwork.SOPHORA.register().addRecipes(consumer, null);
        SDWoodwork.JUJUBE.register().addRecipes(consumer, null);
        addChest(SDBlocks.COTINUS_CHEST, consumer);
        addChest(SDBlocks.JUJUBE_CHEST, consumer);
        addChest(SDBlocks.SOPHORA_CHEST, consumer);
        addStick(SDTrees.COTINUS, SDItems.STICK_COTINUS, consumer);
        addStick(SDTrees.JUJUBE, SDItems.STICK_JUJUBE, consumer);
        addStick(SDTrees.SOPHORA, SDItems.STICK_SOPHORA, consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Tags.Items.STONE), SDItems.SMOOTH_STONE.get(), 0.1f, 200)
                .group(SinoDivination.MOD_ID)
                .unlockedBy("has_stone", has(Tags.Items.STONE))
                .save(consumer);
        shaped(SDBlocks.KETTLE_POT, Blocks.CAULDRON)
                .pattern("Y Y")
                .pattern("ZAZ")
                .pattern("ZZZ")
                .define('A', Blocks.CAULDRON)
                .define('Y', Tags.Items.INGOTS_IRON)
                .define('Z', Tags.Items.STONE)
                .save(consumer);
        shaped(SDItems.HOOK, Items.STICK)
                .pattern("YY")
                .pattern("Y ")
                .pattern("Y ")
                .define('Y', Items.STICK)
                .save(consumer);
        shapeless(Items.GUNPOWDER, SDItems.SULPHUR)
                .requires(Ingredient.of(SDItems.SULPHUR.get()))
                .requires(Ingredient.of(SDItems.NITER.get()))
                .requires(Ingredient.of(ItemTags.COALS))
                .save(consumer);
        ChangeSoupRecipe.builder(Blocks.BIRCH_SAPLING, SDTrees.COTINUS.sapling).save(consumer);
        ChangeSoupRecipe.builder(Blocks.OAK_SAPLING, SDTrees.JUJUBE.sapling).save(consumer);
        ChangeSoupRecipe.builder(Blocks.SPRUCE_SAPLING, SDTrees.SOPHORA.sapling).save(consumer);
        ChangeSoupRecipe.builder(Blocks.POPPY, SDBlocks.ZHU_CAO).save(consumer);
        ChangeSoupRecipe.builder(Blocks.CAVE_VINES, SDBlocks.BRIGHT_STEM_GRASS).save(consumer);
        KettlePotRecipe.builder(SDItems.CHANGE_SOUP, 3)
                .input(3, Items.BONE_MEAL)
                .input(Items.EGG)
                .input(Items.FERMENTED_SPIDER_EYE)
                .save(consumer);
        KettlePotRecipe.builder(SDItems.STICK_RICE)
                .input(3, SDItems.RICE)
                .input(SDItems.JUJUBE)
                .input(SDItems.WORMWOOD_LEAF)
                .save(consumer);
    }

    private ShapedRecipeBuilder shaped(RegistryObject<? extends ItemLike> result, ItemLike unlockedBy) {
        return ShapedRecipeBuilder.shaped(result.get())
                .group(SinoDivination.MOD_ID)
                .unlockedBy("has_block", has(unlockedBy));
    }

    private ShapelessRecipeBuilder shapeless(ItemLike result, RegistryObject<? extends ItemLike> unlockedBy) {
        return ShapelessRecipeBuilder.shapeless(result)
                .group(SinoDivination.MOD_ID)
                .unlockedBy("has_block", has(unlockedBy.get()));
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

    private <T extends Item> void addStick(Tree tree, RegistryObject<T> stick, Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ShapedRecipeBuilder.shaped(stick.get())
                .group(SinoDivination.MOD_ID)
                .pattern("X")
                .pattern("Y")
                .pattern("X")
                .define('X', tree.wood())
                .define('Y', Items.STICK)
                .unlockedBy("has_block", has(tree.wood()))
                .save(pFinishedRecipeConsumer);
    }
}
