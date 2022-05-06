package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinocore.api.data.BlockStateProviderBase;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.block.WoodenChest;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

public class SDBlockStateProvider extends BlockStateProviderBase {

    public SDBlockStateProvider(GatherDataEvent event) {
        super(event.getGenerator(), SinoDivination.MOD_ID, event.getExistingFileHelper(), SDBlocks.REGISTRY);
    }

    @Override
    protected void registerStatesAndModels() {
        SDTrees.COTINUS.register().addBlockModels(this);
        SDTrees.JUJUBE.register().addBlockModels(this);
        SDTrees.SOPHORA.register().addBlockModels(this);
        SDWoodwork.COTINUS.register().addBlockModels(this);
        SDWoodwork.JUJUBE.register().addBlockModels(this);
        SDWoodwork.SOPHORA.register().addBlockModels(this);
        chest(SDBlocks.COTINUS_CHEST);
        chest(SDBlocks.JUJUBE_CHEST);
        chest(SDBlocks.SOPHORA_CHEST);
        setAdding(false);
        super.registerStatesAndModels();
    }

    protected <T extends WoodenChest> void chest(RegistryObject<T> chestObj) {
        T chest = chestObj.get();
        simpleBlock(chest, models().getBuilder(chest.delegate.name().getPath()).texture("particle", blockTexture(chest.planks())));
    }
}
