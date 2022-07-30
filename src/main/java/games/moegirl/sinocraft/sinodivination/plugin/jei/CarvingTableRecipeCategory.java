package games.moegirl.sinocraft.sinodivination.plugin.jei;

import games.moegirl.sinocraft.sinocore.api.utility.texture.SlotEntry;
import games.moegirl.sinocraft.sinocore.api.utility.texture.SlotsEntry;
import games.moegirl.sinocraft.sinocore.api.utility.texture.TextureEntry;
import games.moegirl.sinocraft.sinodivination.recipe.CarvingTableRecipe;
import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import games.moegirl.sinocraft.sinodivination.data.SDLangKeys;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;

import static games.moegirl.sinocraft.sinodivination.menu.CarvingTableMenu.TEXTURE;

public class CarvingTableRecipeCategory extends AbstractRecipeCategory<CarvingTableRecipe> {

    private static final TextureEntry JEI = TEXTURE.textures().ensureGet("jei");
    private static final SlotsEntry INPUTS = TEXTURE.slots().ensureGet("input");
    private static final SlotEntry DYE = TEXTURE.slot().ensureGet("dye");
    private static final SlotEntry OUTPUT = TEXTURE.slot().ensureGet("output");

    public CarvingTableRecipeCategory(IRecipeCategoryRegistration registration) {
        super(registration, JEIPlugin.CARVING_TABLE, SDRecipes.CARVING_TABLE, SDLangKeys.JEI_RECIPE_CARVING_TABLE);
    }

    @Override
    protected IDrawable buildBackground(IGuiHelper guiHelper) {
        return guiHelper.createDrawable(TEXTURE.texture(), (int) JEI.u(), (int) JEI.v(), JEI.w(), JEI.h());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CarvingTableRecipe recipe, IFocusGroup focuses) {
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                Ingredient input = recipe.getInput(row, column);
                if (input.isEmpty()) continue;
                int idx = row * 4 + column;
                SlotEntry entry = INPUTS.entries().get(idx);
                addSlot(builder, RecipeIngredientRole.INPUT, entry, input);
            }
        }
        if (!recipe.getDye().isEmpty()) {
            addSlot(builder, RecipeIngredientRole.INPUT, DYE, recipe.getDye());
        }
        addSlot(builder, RecipeIngredientRole.OUTPUT, OUTPUT, recipe.getResultItem());
    }

    private void addSlot(IRecipeLayoutBuilder builder, RecipeIngredientRole role, SlotEntry slot, Ingredient item) {
        builder.addSlot(role, slot.x() - JEI.x(), slot.y() - JEI.y()).addIngredients(VanillaTypes.ITEM_STACK, Arrays.asList(item.getItems()));
    }

    private void addSlot(IRecipeLayoutBuilder builder, RecipeIngredientRole role, SlotEntry slot, ItemStack item) {
        builder.addSlot(role, slot.x() - JEI.x(), slot.y() - JEI.y()).addIngredient(VanillaTypes.ITEM_STACK, item);
    }
}
