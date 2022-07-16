package games.moegirl.sinocraft.sinodivination.recipe;

import games.moegirl.sinocraft.sinocore.api.crafting.RecipeContainer;
import games.moegirl.sinocraft.sinocore.api.crafting.RecipeHolder;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static games.moegirl.sinocraft.sinodivination.SinoDivination.MOD_ID;

public class SDRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);

    public static RecipeHolder<Container, ChangeSoupRecipe, ChangeSoupRecipeSerializer> CHANGE_SOUP = RecipeHolder.register(REGISTRY, SDItems.CHANGE_SOUP, ChangeSoupRecipeSerializer.INSTANCE);

    public static RecipeHolder<RecipeContainer, KettlePotRecipe, KettlePotRecipeSerializer> KETTLE_POT = RecipeHolder.register(REGISTRY, SDItems.KETTLE_POT, KettlePotRecipeSerializer.INSTANCE);

    public static RecipeHolder<AltarRecipeContainer, AltarRecipe, AltarRecipeSerializer> ALTAR = RecipeHolder.register(REGISTRY, SDBlocks.TRIPOD, AltarRecipeSerializer.INSTANCE);

    public static RecipeHolder<CarvingTableRecipeContainer, CarvingTableRecipe, CarvingTableRecipeSerializer> CARVING_TABLE = RecipeHolder.register(REGISTRY, SDBlocks.CARVING_TABLE, CarvingTableRecipeSerializer.INSTANCE);
}
