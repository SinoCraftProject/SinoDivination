package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class SDItemTagProvider extends ItemTagsProvider {

    public SDItemTagProvider(GatherDataEvent event, SDBlockTagProvider provider) {
        super(event.getGenerator(), provider, SinoDivination.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags() {
        SDTrees.REGISTER.addItemTags(this::tag);
    }
}
