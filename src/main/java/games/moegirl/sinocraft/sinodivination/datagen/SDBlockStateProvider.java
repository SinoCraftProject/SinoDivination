package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinocore.api.data.gen.BlockStateProviderBase;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class SDBlockStateProvider extends BlockStateProviderBase {

    public SDBlockStateProvider(GatherDataEvent event) {
        super(event.getGenerator(), SinoDivination.MOD_ID, event.getExistingFileHelper(), SDBlocks.REGISTRY);
    }

    @Override
    protected void registerStatesAndModels() {
        SDTrees.REGISTER.addBlockModels(this);
        setAdding(false);
        super.registerStatesAndModels();
    }
}
