package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.block.base.WoodenChest;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import games.moegirl.sinocraft.sinodivination.util.SDTags;
import net.minecraft.data.tags.ItemTagsProvider;
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
        tag(SDTags.JADE_SACRIFICIAL_UTENSIL).add(SDItems.CANG_BI.get(), SDItems.HUANG_CONG.get(), SDItems.QING_GUI.get(), SDItems.CHI_ZHANG.get(), SDItems.BAI_HU.get(), SDItems.XUAN_HUANG.get());
        tag(SDTags.COPPER_SACRIFICIAL_UTENSIL).add(SDItems.COPPER_GOBLET.get(), SDItems.COPPER_DAGGER_AXE.get(), SDItems.COPPER_MIRROR.get(), SDItems.COPPER_MASK.get(), SDItems.COPPER_LAMP.get(), SDItems.COPPER_BEAST.get());
        tag(SDTags.SACRIFICIAL_UTENSIL_MATERIAL).add(SDItems.JADE.get()).addTag(Tags.Items.INGOTS_COPPER);
        tag(SDTags.SACRIFICIAL_UTENSIL).addTag(SDTags.COPPER_SACRIFICIAL_UTENSIL).addTag(SDTags.JADE_SACRIFICIAL_UTENSIL);
    }

    protected <T extends WoodenChest> void chest(RegistryObject<T> chestObj) {
        T chest = chestObj.get();
        tag(Tags.Items.CHESTS_WOODEN).add(chest.asItem());
        if (chest.isTrapped) {
            tag(Tags.Items.CHESTS_TRAPPED).add(chest.asItem());
        }
    }
}
