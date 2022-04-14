package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class SDBlockTags {

    public static final TagKey<Block> COTINUS_WOODEN = TagKey.create(Registry.BLOCK_REGISTRY,
            new ResourceLocation(SinoDivination.MOD_ID, "wooden_cotinus"));
}
