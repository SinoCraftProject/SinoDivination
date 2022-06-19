package games.moegirl.sinocraft.sinodivination.recipe;

import games.moegirl.sinocraft.sinocore.api.crafting.SimpleRecipeBuilder;
import games.moegirl.sinocraft.sinocore.api.crafting.ingredient.FluidIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.RegistryObject;

import static games.moegirl.sinocraft.sinodivination.recipe.KettlePotRecipe.IngredientEntry.EMPTY;

public class KettlePotRecipeBuilder extends SimpleRecipeBuilder<KettlePotRecipe, KettlePotRecipeBuilder> {

    private final KettlePotRecipe.IngredientEntry[] inputs = new KettlePotRecipe.IngredientEntry[]{EMPTY, EMPTY, EMPTY};
    private Ingredient container = Ingredient.of(Items.BOWL);
    private FluidIngredient fluid = new FluidIngredient(Fluids.WATER, 1000);
    private ItemStack output = ItemStack.EMPTY;

    public KettlePotRecipeBuilder(ResourceLocation id) {
        super(id);
    }

    public KettlePotRecipeBuilder addInput(int index, Ingredient item) {
        return addInput(index, 1, item);
    }

    public KettlePotRecipeBuilder addInput(int index, int count, Ingredient item) {
        inputs[index] = new KettlePotRecipe.IngredientEntry(count, item);
        return this;
    }

    public KettlePotRecipeBuilder addInput(int index, ItemLike... items) {
        return addInput(index, Ingredient.of(items));
    }

    public KettlePotRecipeBuilder addInput(int index, int count, ItemLike... items) {
        return addInput(index, count, Ingredient.of(items));
    }

    public KettlePotRecipeBuilder addInput(int index, TagKey<Item> item) {
        return addInput(index, Ingredient.of(item));
    }

    public KettlePotRecipeBuilder addInput(int index, int count, TagKey<Item> item) {
        return addInput(index, count, Ingredient.of(item));
    }

    public KettlePotRecipeBuilder addInput(int index, RegistryObject<? extends ItemLike> item) {
        return addInput(index, Ingredient.of(item.get()));
    }

    public KettlePotRecipeBuilder addInput(int index, int count, RegistryObject<? extends ItemLike> item) {
        return addInput(index, count, Ingredient.of(item.get()));
    }

    public KettlePotRecipeBuilder container(Ingredient ingredient) {
        container = ingredient;
        return this;
    }

    public KettlePotRecipeBuilder fluid(Fluid fluid, int amount) {
        this.fluid = new FluidIngredient(fluid, amount);
        return this;
    }

    public KettlePotRecipeBuilder fluid(Fluid fluid) {
        return fluid(fluid, 1000);
    }

    public KettlePotRecipeBuilder fluid(TagKey<Fluid> fluid, int amount) {
        this.fluid = new FluidIngredient(fluid, amount);
        return this;
    }

    public KettlePotRecipeBuilder fluid(TagKey<Fluid> fluid) {
        return fluid(fluid, 1000);
    }

    public KettlePotRecipeBuilder output(ItemStack item) {
        this.output = item;
        return this;
    }

    @Override
    public KettlePotRecipe build() {
        return new KettlePotRecipe(id, inputs, container, fluid, output);
    }
}
