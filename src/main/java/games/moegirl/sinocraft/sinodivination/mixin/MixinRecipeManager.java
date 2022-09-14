package games.moegirl.sinocraft.sinodivination.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class MixinRecipeManager {

    @Shadow
    private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;
    @Shadow
    private Map<ResourceLocation, Recipe<?>> byName;

    @Shadow
    private boolean hasErrors;

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
        Map<ResourceLocation, Recipe<?>> byNameMap = new HashMap<>(byName.size());
        Map<ResourceLocation, Recipe<?>> blasting = new HashMap<>(recipes.getOrDefault(RecipeType.BLASTING, Collections.emptyMap()));

        recipes.forEach((key, value) -> {
            if (key == RecipeType.SMELTING) {
                HashMap<ResourceLocation, Recipe<?>> smeltingMap = new HashMap<>();
                var blastingRecipePairs = blasting.values().stream()
                        .map(r -> Pair.of(r, r.getIngredients().get(0).getItems())).toList();
                value.forEach((id, recipe) -> {
                    Ingredient ingredient = recipe.getIngredients().get(0);
                    ItemStack[] stacks = ingredient.getItems();
                    if (Arrays.stream(stacks).allMatch(is -> is.is(Tags.Items.ORES))) {
                        SmeltingRecipe r = (SmeltingRecipe) recipe;
                        boolean skip = false, found = false;
                        ItemStack output = recipe.getResultItem();
                        for (var brp : blastingRecipePairs) {
                            Recipe<?> br = brp.getFirst();
                            if (sinodivination$areIngredientEquals(r.getIngredients().get(0), stacks, br.getIngredients().get(0), brp.getSecond())) {
                                found = true;
                                if (ItemStack.isSameItemSameTags(br.getResultItem(), output)) {
                                    skip = true;
                                    System.out.println("  Skip: " + Arrays.toString(r.getIngredients().get(0).getItems()) + " -> " + output + ", " + r.getCookingTime());
                                }
                                break;
                            }
                        }
                        if (!found) {
                            BlastingRecipe newRecipe = new BlastingRecipe(id, recipe.getGroup(), ingredient, output, r.getExperience(), r.getCookingTime());
                            blasting.put(id, newRecipe);
                            byNameMap.put(id, newRecipe);
                            System.out.println("  Replace: " + Arrays.toString(r.getIngredients().get(0).getItems()) + " -> " + output + ", " + r.getCookingTime());
                        } else if (!skip) {
                            smeltingMap.put(id, recipe);
                            byNameMap.put(id, recipe);
                            System.out.println("  Keep: " + Arrays.toString(r.getIngredients().get(0).getItems()) + " -> " + output + ", " + r.getCookingTime());
                        }
                    } else {
                        smeltingMap.put(id, recipe);
                        byNameMap.put(id, recipe);
                    }
                });
                map.put(RecipeType.SMELTING, smeltingMap);
            } else if (key == RecipeType.BLASTING) {
                map.put(RecipeType.BLASTING, blasting);
                byNameMap.putAll(blasting);
            } else {
                map.put(key, value);
                byNameMap.putAll(value);
            }
        });
        this.recipes = ImmutableMap.copyOf(map);
        this.byName = ImmutableMap.copyOf(byNameMap);
    }

    private boolean sinodivination$areIngredientEquals(Ingredient ingredient1, ItemStack[] stacks1, Ingredient ingredient2, ItemStack[] stacks2) {
        if (ingredient1.equals(ingredient2) || ingredient2.equals(ingredient1)) {
            return true;
        }
        for (ItemStack stack : stacks1) {
            boolean found = false;
            for (ItemStack itemStack : stacks2) {
                if (ItemStack.isSameItemSameTags(stack, itemStack)) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }
}
