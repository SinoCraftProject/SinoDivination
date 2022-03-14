package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class SDBlockTags {

    public static final Tag.Named<Block> COTINUS_WOODEN = ForgeTagHandler.createOptionalTag(ForgeRegistries.BLOCKS,
            new ResourceLocation(SinoDivination.MOD_ID, "wooden_cotinus"));
}
