package games.moegirl.sinocraft.sinodivination.util;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class SDTags {

    // Items ===========================================================================================================

    public static final TagKey<Item> SACRIFICIAL_VESSEL = item("sacrificial_vessel");

    // Blocks ==========================================================================================================

    // todo by lq2007: 无患木方块阻挡鬼魂通过
    public static final TagKey<Block> COTINUS_BLOCK = block("cotinus");

    // todo by lq2007: 破坏时在玩家附近生成木鬼
    public static final TagKey<Block> SOPHORA_BLOCK = block("sophora");

    // todo by lq2007: 艾灸测试
    public static final TagKey<Block> FIRE_SOURCE = block("fire_source");

    public static final TagKey<Block> HEAT_SOURCE = block("heat");

    // =================================================================================================================

    public static TagKey<Block> block(String name) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(SinoDivination.MOD_ID, "block/" + name));
    }

    public static TagKey<Item> item(String name) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(SinoDivination.MOD_ID, "item/" + name));
    }
}
