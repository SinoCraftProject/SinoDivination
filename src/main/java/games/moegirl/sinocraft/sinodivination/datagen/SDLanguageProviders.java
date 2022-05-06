package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinocore.api.data.LanguageProviderBase;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.item.DivinationTab;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import net.minecraft.data.DataGenerator;

public class SDLanguageProviders extends LanguageProviderBase {

    public SDLanguageProviders(DataGenerator generator) {
        super(generator, SinoDivination.MOD_ID);
    }

    @Override
    protected void addTranslations() {
        // group
        addGroup(DivinationTab.INSTANCE, "SinoDivination", "华夏九筮");
        // blocks
        addBlock(SDBlocks.ORE_JADE, "Jade Ore", "玉矿石");
        addBlock(SDBlocks.ORE_NITER, "Niter Ore", "硝石矿石");
        addBlock(SDBlocks.ORE_SULPHUR, "Sulphur Ore", "硫磺矿石");
        addBlock(SDBlocks.COTINUS_CHEST, "Cotinus Chest", "无患木匣");
        addBlock(SDBlocks.JUJUBE_CHEST, "Jujube Chest", "枣木匣");
        addBlock(SDBlocks.SOPHORA_CHEST, "Sophora Chest", "槐木匣");
        addBlock(SDBlocks.ARGY_WORMWOOD, "Argy Wormwood", "艾草");
        addBlock(SDBlocks.GARLIC, "Garlic", "大蒜");
        // item
        addItem(SDItems.JADE, "Jade", "玉石");
        addItem(SDItems.NITER, "Niter", "硝石");
        addItem(SDItems.SULPHUR, "Sulphur", "硫磺");
        addItem(SDItems.GHOST_KILLING_STICK, "Ghost Killing Stick", "鬼杀棒");
        addItem(SDItems.CHANGING_SEEDING_DECOCTION, "Changing Seeding Decoction", "变荑汤");
        addItem(SDItems.DIVINE_POWER_STICK, "Divine Power Stick", "神力棒");
        addItem(SDItems.LIFE_SYMBOL, "Life Symbol", "命符");
        addItem(SDItems.JUJUBE, "Jujube", "枣");
        addItem(SDItems.GHOST_GUARD_STICK, "Ghost Guard Stick", "御鬼棒");
        addItem(SDItems.ARGY_WORMWOOD_SEED, "Argy Wormwood Seed", "艾草种子");
        addItem(SDItems.ARGY_WORMWOOD_LEAVES, "Argy Wormwood Leaves", "艾草叶");
        addItem(SDItems.MOXIBUSTION, "Moxibustion", "艾灸");
        addItem(SDItems.GARLIC_SEED, "Garlic Seed", "大蒜种子");
        addItem(SDItems.GARLIC, "Garlic", "大蒜");
        // other
        SDTrees.COTINUS.register().addLanguagesEn(en);
        SDTrees.COTINUS.register().addLanguagesZh(zh, "无患");
        SDTrees.JUJUBE.register().addLanguagesEn(en);
        SDTrees.JUJUBE.register().addLanguagesZh(zh, "枣");
        SDTrees.SOPHORA.register().addLanguagesEn(en);
        SDTrees.SOPHORA.register().addLanguagesZh(zh, "槐");
        SDWoodwork.COTINUS.register().addLanguagesEn(en);
        SDWoodwork.COTINUS.register().addLanguagesZh(zh, "无患");
        SDWoodwork.JUJUBE.register().addLanguagesEn(en);
        SDWoodwork.JUJUBE.register().addLanguagesZh(zh, "枣");
        SDWoodwork.SOPHORA.register().addLanguagesEn(en);
        SDWoodwork.SOPHORA.register().addLanguagesZh(zh, "槐");
    }
}
