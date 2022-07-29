package games.moegirl.sinocraft.sinodivination.data.provider;

import games.moegirl.sinocraft.sinocore.api.data.ItemModelProviderBase;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.block.base.WoodenChest;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

public class SDItemModelProvider extends ItemModelProviderBase {

    public SDItemModelProvider(GatherDataEvent event) {
        super(event.getGenerator(), SinoDivination.MOD_ID, event.getExistingFileHelper(), SDItems.REGISTRY);
    }

    @Override
    protected void registerItemModels() {
        SDTrees.COTINUS.register().addItemModels(this);
        SDTrees.JUJUBE.register().addItemModels(this);
        SDTrees.SOPHORA.register().addItemModels(this);
        SDWoodwork.COTINUS.register().addItemModels(this);
        SDWoodwork.JUJUBE.register().addItemModels(this);
        SDWoodwork.SOPHORA.register().addItemModels(this);
        chest(SDBlocks.COTINUS_CHEST);
        chest(SDBlocks.JUJUBE_CHEST);
        chest(SDBlocks.SOPHORA_CHEST);

        generatedItem(SDItems.RICE.get());
        generatedItem(SDItems.REHMANNIA.get());
        generatedItem(SDItems.SESAME.get());
        generatedItem(SDItems.DRAGONLIVER_MELON.get());
        generatedItem(SDItems.WORMWOOD_LEAF.get());
        generatedItem(SDItems.GARLIC.get());
    }

    protected <T extends WoodenChest> void chest(RegistryObject<T> chestObj) {
        T chest = chestObj.get();
        ResourceLocation name = chest.planks().delegate.name();
        ResourceLocation texPlank = new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
        singleTexture(chest.delegate.name().getPath(), mcLoc("item/chest"), "particle", texPlank);
    }
}
