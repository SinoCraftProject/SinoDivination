package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinocore.api.data.LootTableProviderBase;
import games.moegirl.sinocraft.sinocore.api.tree.TreeBlockLoot;
import games.moegirl.sinocraft.sinocore.api.woodwork.WoodworkBlockLoot;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import net.minecraft.data.DataGenerator;

public class SDLootTableProvider extends LootTableProviderBase {

    public SDLootTableProvider(DataGenerator pGenerator) {
        super(pGenerator, SinoDivination.MOD_ID, SDBlocks.REGISTRY);
    }

    @Override
    public void addLootTables() {
        addBlockTable(new TreeBlockLoot(SDTrees.COTINUS));
        addBlockTable(new TreeBlockLoot(SDTrees.SOPHORA));
        addBlockTable(new TreeBlockLoot(SDTrees.JUJUBE));
        addBlockTable(new WoodworkBlockLoot(SDWoodwork.COTINUS));
        addBlockTable(new WoodworkBlockLoot(SDWoodwork.SOPHORA));
        addBlockTable(new WoodworkBlockLoot(SDWoodwork.JUJUBE));
    }
}
