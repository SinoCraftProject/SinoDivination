package games.moegirl.sinocraft.sinodivination.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryManager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RecipeSerializers {

    public static final RegistryOps<JsonElement> JSON_OPS = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get());

    @SuppressWarnings("ConstantConditions")
    public static Block readBlock(JsonObject json, String key) {
        return RegistryManager.ACTIVE
                .getRegistry(Registry.BLOCK_REGISTRY)
                .getValue(new ResourceLocation(json.get(key).getAsString()));
    }

    public static Block readBlock(FriendlyByteBuf buf) {
        return RegistryManager.ACTIVE.getRegistry(Registry.BLOCK_REGISTRY).getValue(buf.readVarInt());
    }

    public static Ingredient[] readIngredients(JsonObject json, String key) {
        JsonArray array = json.getAsJsonArray(key);
        Ingredient[] ingredients = new Ingredient[array.size()];
        for (int i = 0; i < array.size(); i++) {
            ingredients[i] = Ingredient.fromJson(array.get(i));
        }
        return ingredients;
    }

    public static Ingredient[] readIngredients(FriendlyByteBuf buf) {
        Ingredient[] result = new Ingredient[buf.readVarInt()];
        for (int i = 0; i < result.length; i++) {
            result[i] = Ingredient.fromNetwork(buf);
        }
        return result;
    }

    public static <T> Optional<T> read(Decoder<T> decoder, JsonObject json, String key) {
        return decoder.decode(JSON_OPS, json.get(key)).result().map(Pair::getFirst);
    }

    @SuppressWarnings("ConstantConditions")
    public static void write(JsonObject json, String key, Block value) {
        json.addProperty(key, RegistryManager.ACTIVE.getRegistry(Registry.BLOCK_REGISTRY).getKey(value).toString());
    }

    public static void write(FriendlyByteBuf buf, Block value) {
        buf.writeVarInt(RegistryManager.ACTIVE.getRegistry(Registry.BLOCK_REGISTRY).getID(value));
    }

    public static void write(JsonObject json, String key, Ingredient[] value) {
        JsonArray array = new JsonArray();
        for (Ingredient ingredient : value) {
            array.add(ingredient.toJson());
        }
        json.add(key, array);
    }

    public static void write(FriendlyByteBuf buf, Ingredient[] values) {
        buf.writeVarInt(values.length);
        for (Ingredient value : values) {
            value.toNetwork(buf);
        }
    }

    public static <T> void write(JsonObject json, String key, Encoder<T> encoder, T value) {
        json.add(key, encoder.encodeStart(JSON_OPS, value).result().orElseThrow());
    }

    public static void requireKeys(JsonObject json, ResourceLocation type, ResourceLocation id, String... keys) {
        List<String> result = Arrays.stream(keys).filter(k -> !json.has(k)).toList();
        if (!result.isEmpty()) {
            throw new RuntimeException("Recipe " + id + " (type: " + type + ") need keys: " + String.join(", ", result));
        }
    }
}
