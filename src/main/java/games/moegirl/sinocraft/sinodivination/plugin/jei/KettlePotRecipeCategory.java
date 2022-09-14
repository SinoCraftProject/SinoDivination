package games.moegirl.sinocraft.sinodivination.plugin.jei;

import games.moegirl.sinocraft.sinodivination.data.SDLangKeys;
import games.moegirl.sinocraft.sinodivination.recipe.KettlePotRecipe;
import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeCategoryRegistration;

import java.util.List;

public class KettlePotRecipeCategory extends AbstractRecipeCategory<KettlePotRecipe> {

    public KettlePotRecipeCategory(IRecipeCategoryRegistration registration) {
        super(registration, JEIPlugin.KETTLE_POT, SDRecipes.KETTLE_POT, SDLangKeys.JEI_RECIPE_KETTLE_POT);
    }

    @Override
    protected IDrawable buildBackground(IGuiHelper guiHelper) {
        return guiHelper.drawableBuilder(texture("jei", "recipe", "kettle_pot"), 0, 0, 120, 120)
                .setTextureSize(120, 120)
                .build();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, KettlePotRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 16, 15).addIngredients(VanillaTypes.ITEM_STACK, List.of(recipe.getInput(0).getItems()));
        builder.addSlot(RecipeIngredientRole.INPUT, 52, 2).addIngredients(VanillaTypes.ITEM_STACK, List.of(recipe.getInput(1).getItems()));
        builder.addSlot(RecipeIngredientRole.INPUT, 88, 15).addIngredients(VanillaTypes.ITEM_STACK, List.of(recipe.getInput(2).getItems()));
        builder.addSlot(RecipeIngredientRole.INPUT, 52, 100).addIngredients(ForgeTypes.FLUID_STACK, recipe.getFluid().allStacks());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 52, 71).addIngredient(VanillaTypes.ITEM_STACK, recipe.getResultItem());
    }
}
