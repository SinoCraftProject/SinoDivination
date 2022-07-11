package games.moegirl.sinocraft.sinodivination.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import games.moegirl.sinocraft.sinocore.api.SinoCoreAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class MixinRecipeManager {

    @Shadow private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;

    @Shadow private Map<ResourceLocation, Recipe<?>> byName;

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("TAIL"))
    protected void injectApply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler, CallbackInfo ci) {
        // original smelting recipes
        Map<ResourceLocation, Recipe<?>> sm = recipes.get(RecipeType.SMELTING);
        int reloadCount = 0;
        if (sm != null && !sm.isEmpty()) {
            // new smelting recipes
            HashMap<ResourceLocation, Recipe<?>> nr = new HashMap<>(sm.size());
            // deleted (ore) smelting recipes
            HashMap<ResourceLocation, SmeltingRecipe> dr = new HashMap<>();
            sm.forEach((name, recipe) -> {
                if (recipe instanceof SmeltingRecipe sr) {
                    Ingredient ingredient = sr.getIngredients().get(0);
                    if (Arrays.stream(ingredient.getItems()).allMatch(is -> is.is(Tags.Items.ORES))) {
                        dr.put(name, sr);
                    } else {
                        nr.put(name, recipe);
                    }
                } else {
                    nr.put(name, recipe);
                }
            });
            if (!dr.isEmpty()) {
                // blasting recipes
                Map<ResourceLocation, Recipe<?>> bm = new HashMap<>(recipes.getOrDefault(RecipeType.BLASTING, new HashMap<>()));
                dr.forEach((name, recipe) -> {
                    Ingredient ingredient = recipe.getIngredients().get(0);
                    JsonElement json = ingredient.toJson();
                    if (bm.values().stream()
                            .map(r -> r.getIngredients().get(0))
                            .map(Ingredient::toJson)
                            .noneMatch(r -> r.equals(json))) {
                        ResourceLocation id = recipe.getId();
                        bm.put(name, new BlastingRecipe(id, recipe.getGroup(), ingredient, recipe.getResultItem(), recipe.getExperience(), recipe.getCookingTime()));
                    }
                });
                reloadCount = dr.size();

                Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> newRecipes = new HashMap<>(recipes);
                newRecipes.put(RecipeType.SMELTING, ImmutableMap.copyOf(nr));
                newRecipes.put(RecipeType.BLASTING, ImmutableMap.copyOf(bm));
                recipes = ImmutableMap.copyOf(newRecipes);
                ImmutableMap.Builder<ResourceLocation, Recipe<?>> builder = new ImmutableMap.Builder<>();
                recipes.values().stream()
                        .map(Map::values)
                        .flatMap(Collection::stream)
                        .forEach(r -> builder.put(r.getId(), r));
                byName = builder.build();
            }
        }
        SinoCoreAPI.LOGGER.info("Reloaded {} recipes", reloadCount);
    }
}
