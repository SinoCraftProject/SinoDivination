package games.moegirl.sinocraft.sinodivination.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.api.crafting.AbstractRecipeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;

public final class AltarRecipeSerializer extends AbstractRecipeSerializer<AltarRecipe> {

    public static final AltarRecipeSerializer INSTANCE = new AltarRecipeSerializer();

    @Override
    public void toJson(JsonObject jsonObject, AltarRecipe altarRecipe) {
        JsonArray sacrificialVessels = new JsonArray();
        for (Item sacrificialVessel : altarRecipe.sacrificialVessels) {
            if (sacrificialVessel == Items.AIR) {
                continue;
            }
            sacrificialVessels.add(Registry.ITEM.getKey(sacrificialVessel).toString());
        }
        jsonObject.add("sacrificialVessels", sacrificialVessels);
        jsonObject.add("base", altarRecipe.base.toJson());
        RecipeSerializers.write(jsonObject, "result", ItemStack.CODEC, altarRecipe.getResultItem());
    }

    @Override
    public AltarRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        RecipeSerializers.requireKeys(pSerializedRecipe, SDRecipes.ALTAR.name(), pRecipeId, "base", "result");
        Item[] sacrificialVessels = new Item[4];
        Arrays.fill(sacrificialVessels, Items.AIR);
        if (pSerializedRecipe.has("sacrificialVessels")) {
            JsonArray array = pSerializedRecipe.getAsJsonArray("sacrificialVessels");
            int count = Math.min(4, array.size());
            for (int i = 0; i < count; i++) {
                sacrificialVessels[i] = Registry.ITEM.getOptional(new ResourceLocation(array.get(i).getAsString())).orElseThrow();
            }
        }
        Ingredient base = Ingredient.fromJson(pSerializedRecipe.get("base"));
        ItemStack result = RecipeSerializers.read(ItemStack.CODEC, pSerializedRecipe, "result").orElseThrow();
        return new AltarRecipe(pRecipeId, sacrificialVessels, base, result);
    }

    @Override
    public AltarRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        Item[] sacrificialVessels = new Item[4];
        Arrays.fill(sacrificialVessels, Items.AIR);
        int count = Math.min(4, pBuffer.readByte());
        for (int i = 0; i < count; i++) {
            sacrificialVessels[i] = Registry.ITEM.byId(pBuffer.readVarInt());
        }
        Ingredient base = Ingredient.fromNetwork(pBuffer);
        ItemStack result = pBuffer.readItem();
        return new AltarRecipe(pRecipeId, sacrificialVessels, base, result);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, AltarRecipe pRecipe) {
        int count = 0;
        for (Item sacrificialVessel : pRecipe.sacrificialVessels) {
            if (sacrificialVessel != Items.AIR) {
                count++;
            }
        }
        pBuffer.writeByte(count);
        for (Item sacrificialVessel : pRecipe.sacrificialVessels) {
            if (sacrificialVessel != Items.AIR) {
                pBuffer.writeVarInt(Item.getId(sacrificialVessel));
            }
        }
        pRecipe.base.toNetwork(pBuffer);
        pBuffer.writeItemStack(pRecipe.getResultItem(), false);
    }
}
