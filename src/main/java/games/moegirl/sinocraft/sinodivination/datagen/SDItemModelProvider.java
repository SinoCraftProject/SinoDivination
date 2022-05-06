package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinocore.api.data.ItemModelProviderBase;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.block.WoodenChest;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

public class SDItemModelProvider extends ItemModelProviderBase {

    public SDItemModelProvider(GatherDataEvent event) {
        super(event.getGenerator(), SinoDivination.MOD_ID, event.getExistingFileHelper(), SDItems.REGISTRY);
    }

    @Override
    protected void registerModels() {
        SDTrees.COTINUS.register().addItemModels(this);
        SDTrees.JUJUBE.register().addItemModels(this);
        SDTrees.SOPHORA.register().addItemModels(this);
        SDWoodwork.COTINUS.register().addItemModels(this);
        SDWoodwork.JUJUBE.register().addItemModels(this);
        SDWoodwork.SOPHORA.register().addItemModels(this);
        chest(SDBlocks.COTINUS_CHEST);
        chest(SDBlocks.JUJUBE_CHEST);
        chest(SDBlocks.SOPHORA_CHEST);

        skipItems(SDTrees.COTINUS.allItems().toArray(Item[]::new));
        skipItems(SDTrees.JUJUBE.allItems().toArray(Item[]::new));
        skipItems(SDTrees.SOPHORA.allItems().toArray(Item[]::new));
        skipItems(SDWoodwork.COTINUS.allItems().toArray(Item[]::new));
        skipItems(SDWoodwork.JUJUBE.allItems().toArray(Item[]::new));
        skipItems(SDWoodwork.SOPHORA.allItems().toArray(Item[]::new));
        skipItems(SDItems.COTINUS_CHEST, SDItems.JUJUBE_CHEST, SDItems.SOPHORA_CHEST);
        super.registerModels();
    }

    protected <T extends WoodenChest> void chest(RegistryObject<T> chestObj) {
        T chest = chestObj.get();
        ResourceLocation name = chest.planks().delegate.name();
        ResourceLocation texPlank = new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
        singleTexture(chest.delegate.name().getPath(), mcLoc("item/chest"), "particle", texPlank);
    }
}
