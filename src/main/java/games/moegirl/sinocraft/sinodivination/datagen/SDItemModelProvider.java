package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinocore.api.data.gen.ItemModelProviderBase;
import games.moegirl.sinocraft.sinocore.api.tree.Tree;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import net.minecraft.world.item.Item;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

public class SDItemModelProvider extends ItemModelProviderBase {

    public SDItemModelProvider(GatherDataEvent event) {
        super(event.getGenerator(), SinoDivination.MOD_ID, event.getExistingFileHelper(), SDItems.REGISTRY);
    }

    @Override
    protected void registerModels() {
        SDTrees.REGISTER.addItemModels(this);
        SDTrees.REGISTER.getAllTrees().values().forEach(this::skipTree);
        super.registerModels();
    }

    private void skipTree(Tree tree) {
        skipItems(tree.getItems().boat.get());
        RegistryObject<Item> stick = tree.getItems().stick;
        if (stick != null) {
            skipItems(stick.get());
        }
        skipItems(tree.getBlocks().door.get().asItem());
        skipItems(tree.getBlocks().fence.get().asItem());
        skipItems(tree.getBlocks().trapdoor.get().asItem());
        skipItems(tree.getBlocks().sign.get().asItem());
    }
}
