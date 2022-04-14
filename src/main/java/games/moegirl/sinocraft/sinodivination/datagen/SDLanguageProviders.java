package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinocore.api.data.LanguageProviderBase;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.item.DivinationTab;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import net.minecraft.data.DataGenerator;

public class SDLanguageProviders extends LanguageProviderBase {

    public SDLanguageProviders(DataGenerator generator) {
        super(generator, SinoDivination.MOD_ID);
    }

    @Override
    protected void addTranslations() {
        addGroup(DivinationTab.INSTANCE, "SinoDivination", "华夏九筮");

        addBlock(SDBlocks.ORE_JADE, "Jade Ore", "玉矿石");
        addBlock(SDBlocks.ORE_NITER, "Niter Ore", "硝石矿石");
        addBlock(SDBlocks.ORE_SULPHUR, "Sulphur Ore", "硫磺矿石");

        addItem(SDItems.JADE, "Jade", "玉石");
        addItem(SDItems.NITER, "Niter", "硝石");
        addItem(SDItems.SULPHUR, "Sulphur", "硫磺");

        addItem(SDItems.GHOST_KILLING_STICK, "Ghost Killing Stick", "鬼杀棒");
        addItem(SDItems.CHANGING_SEEDING_DECOCTION, "Changing Seeding Decoction", "变荑汤");

        SDTrees.REGISTER.addLanguages(this);
    }
}
