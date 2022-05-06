package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.block.WoodenChest;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

public class SDItemTagProvider extends ItemTagsProvider {

    public SDItemTagProvider(GatherDataEvent event, SDBlockTagProvider provider) {
        super(event.getGenerator(), provider, SinoDivination.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags() {
        SDTrees.COTINUS.register().addItemTags(this::tag);
        SDTrees.JUJUBE.register().addItemTags(this::tag);
        SDTrees.SOPHORA.register().addItemTags(this::tag);
        SDWoodwork.COTINUS.register().addItemTags(this::tag);
        SDWoodwork.JUJUBE.register().addItemTags(this::tag);
        SDWoodwork.SOPHORA.register().addItemTags(this::tag);
        chest(SDBlocks.COTINUS_CHEST);
        chest(SDBlocks.JUJUBE_CHEST);
        chest(SDBlocks.SOPHORA_CHEST);
    }

    protected <T extends WoodenChest> void chest(RegistryObject<T> chestObj) {
        T chest = chestObj.get();
        tag(Tags.Items.CHESTS_WOODEN).add(chest.asItem());
        if (chest.isTrapped) {
            tag(Tags.Items.CHESTS_TRAPPED).add(chest.asItem());
        }
    }
}
