package games.moegirl.sinocraft.sinodivination.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class MixinRecipeManager {

    @Shadow private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;
    @Shadow private Map<ResourceLocation, Recipe<?>> byName;

    @Shadow private boolean hasErrors;

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At("RETURN"))
    protected void injectApply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        sinodivination$replaceRecipes();
    }

    @Inject(method = "replaceRecipes", at = @At("RETURN"))
    protected void injectReplaceRecipes(Iterable<Recipe<?>> recipes, CallbackInfo ci) {
        sinodivination$replaceRecipes();
    }

    private void sinodivination$replaceRecipes() {
        if (hasErrors) return;
        Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> map = Maps.newHashMap();
        Map<ResourceLocation, Recipe<?>> byNameMap = new HashMap<>(byName);
        Map<ResourceLocation, Recipe<?>> blasting = new HashMap<>();

        recipes.forEach((key, value) -> {
            if (key == RecipeType.SMELTING) {
                HashMap<ResourceLocation, Recipe<?>> smeltingMap = new HashMap<>();
                value.forEach((id, recipe) -> {
                    ItemStack output = recipe.getResultItem();
                    if (output.is(Tags.Items.INGOTS) || output.is(Tags.Items.NUGGETS) || output.is(Tags.Items.STORAGE_BLOCKS)) {
                        if (Arrays.stream(recipe.getIngredients().get(0).getItems()).allMatch(is -> is.is(Tags.Items.ORES))) {
                            SmeltingRecipe r = (SmeltingRecipe) recipe;
                            BlastingRecipe newRecipe = new BlastingRecipe(id, recipe.getGroup(), recipe.getIngredients().get(0), output, r.getExperience(), r.getCookingTime());
                            blasting.put(id, newRecipe);
                            byNameMap.put(id, newRecipe);
                        } else {
                            smeltingMap.put(id, recipe);
                        }
                    } else {
                        smeltingMap.put(id, recipe);
                    }
                });
                map.put(RecipeType.SMELTING, smeltingMap);
            } else if (key == RecipeType.BLASTING) {
                blasting.putAll(value);
                map.put(RecipeType.BLASTING, blasting);
            } else {
                map.put(key, value);
            }
        });
        this.recipes = ImmutableMap.copyOf(map);
        this.byName = ImmutableMap.copyOf(byNameMap);
    }
}
