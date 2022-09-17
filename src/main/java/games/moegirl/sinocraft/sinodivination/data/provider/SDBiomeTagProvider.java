package games.moegirl.sinocraft.sinodivination.data.provider;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.data.SDTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class SDBiomeTagProvider extends BiomeTagsProvider {

    public SDBiomeTagProvider(DataGenerator arg, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, SinoDivination.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(SDTags.SNOWY_BIOME).add(Biomes.SNOWY_BEACH);
    }
}
