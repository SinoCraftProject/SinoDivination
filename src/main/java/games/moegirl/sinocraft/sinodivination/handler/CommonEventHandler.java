package games.moegirl.sinocraft.sinodivination.handler;

import net.minecraft.world.item.crafting.*;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Mod.EventBusSubscriber
public class CommonEventHandler {

    @SubscribeEvent
    public static void onRecipeUpdate(RecipesUpdatedEvent event) {
        Collection<Recipe<?>> recipes = event.getRecipeManager().getRecipes();
        List<Recipe<?>> newRecipes = new ArrayList<>(recipes.size());
        for (Recipe<?> recipe : recipes) {
            Recipe<?> r = recipe;
            if (recipe.getType() == RecipeType.SMELTING
                    && recipe instanceof SmeltingRecipe sr
                    && !sr.getIngredients().stream().map(Ingredient::getItems).flatMap(Arrays::stream).allMatch(is -> is.is(Tags.Items.ORES))) {
                r = toBlastRecipe(sr);
            }
            newRecipes.add(r);
        }
        event.getRecipeManager().replaceRecipes(newRecipes);
    }

    private static BlastingRecipe toBlastRecipe(SmeltingRecipe sr) {
        return new BlastingRecipe(sr.getId(), sr.getGroup(), sr.getIngredients().get(0), sr.getResultItem(), sr.getExperience(), sr.getCookingTime());
    }
}
