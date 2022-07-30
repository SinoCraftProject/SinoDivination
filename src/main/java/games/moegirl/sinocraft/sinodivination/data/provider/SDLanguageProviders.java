package games.moegirl.sinocraft.sinodivination.data.provider;

import games.moegirl.sinocraft.sinocore.api.data.LanguageProviderBase;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.item.DivinationTab;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import games.moegirl.sinocraft.sinodivination.data.SDLangKeys;
import games.moegirl.sinocraft.sinodivination.util.StringUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SDLanguageProviders extends LanguageProviderBase {

    private static final Logger LOGGER = LogManager.getLogger();

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
        addBlock(SDBlocks.WORMWOOD, "Wormwood", "艾草");
        addBlock(SDBlocks.GARLIC, "Garlic", "大蒜");
        addBlock(SDBlocks.RICE, "Glutinous Rise", "糯稻");
        addBlock(SDBlocks.LUCID_GANODERMA, "Ganoderma Lucidum", "灵芝");
        addBlock(SDBlocks.REHMANNIA, "Rehmannia Glutinosa", "地黄");
        addBlock(SDBlocks.DRAGONLIVER_MELON, "Longan Melon", "龙肝瓜");
        addBlock(SDBlocks.SESAME, "Jusheng", "巨胜");
        addBlock(SDBlocks.ZHU_CAO, "Zhu Cao", "朱草");
        addBlock(SDBlocks.BRIGHT_STEM_GRASS, "Bright Stem Grass", "明茎草");
        addBlock(SDBlocks.BELLOWS, "Bellows", "风箱");
        addBlock(SDBlocks.KETTLE_POT, "Kettle Pot", "釜锅");
        addBlock(SDBlocks.SILKWORM_PLAQUE, "Silkworm Plaque", "蚕匾");
        addBlock(SDBlocks.TRIPOD, "鼎");
        addBlock(SDBlocks.ALTAR, "祭坛");
        addBlock(SDBlocks.CARVING_TABLE, "雕刻台");
        // item
        addItem(SDItems.JADE, "Jade", "玉石");
        addItem(SDItems.NITER, "Niter", "硝石");
        addItem(SDItems.SULPHUR, "Sulphur", "硫磺");
        addItem(SDItems.STICK_COTINUS, "Ghost Killing Stick", "鬼杀棒");
        addItem(SDItems.CHANGE_SOUP, "Changing Seeding Decoction", "变荑汤");
        addItem(SDItems.STICK_JUJUBE, "Divine Power Stick", "神力棒");
        addItem(SDItems.LIFE_SYMBOL, "Life Symbol", "命符");
        addItem(SDItems.JUJUBE, "Jujube", "枣");
        addItem(SDItems.STICK_SOPHORA, "Ghost Guard Stick", "御鬼棒");
        addItem(SDItems.SEED_WORMWOOD, "Argy Wormwood Seed", "艾草种子");
        addItem(SDItems.WORMWOOD_LEAF, "Argy Wormwood Leaves", "艾草叶");
        addItem(SDItems.MOXIBUSTION, "Moxibustion", "艾灸");
        addItem(SDItems.SEED_GARLIC, "Garlic Seed", "大蒜种子");
        addItem(SDItems.GARLIC, "Garlic", "大蒜");
        addItem(SDItems.RICE, "Rice", "稻米");
        addItem(SDItems.SEED_RICE, "Glutinous Rice Seed", "糯稻种子");
        addItem(SDItems.REHMANNIA, "Rehmannia Glutinosa", "地黄");
        addItem(SDItems.SEED_REHMANNIA, "Rehmannia Seed", "地黄种子");
        addItem(SDItems.DRAGONLIVER_MELON, "Longan Melon", "龙肝瓜");
        addItem(SDItems.SEED_DRAGONLIVER, "Longan Melon Seed", "龙肝瓜种子");
        addItem(SDItems.SESAME, "Sesame", "芝麻");
        addItem(SDItems.SEED_SESAME, "Jusheng Seed", "巨胜种子");
        addItem(SDItems.SILKWORM_BABY, "Silkworm Baby", "蚕宝宝");
        addItem(SDItems.HOOK, "Hook", "钩棍");
        addItem(SDItems.SILK, "Silk", "丝");
        addItem(SDItems.CANG_BI, "Cang Bi", "苍壁");
        addItem(SDItems.HUANG_CONG, "Huang Cong", "黄琮");
        addItem(SDItems.QING_GUI, "Qing Gui", "青珪");
        addItem(SDItems.CHI_ZHANG, "Chi Zhang", "赤璋");
        addItem(SDItems.BAI_HU, "Bai Hu", "白琥");
        addItem(SDItems.XUAN_HUANG, "Xuan Huang", "玄璜");
        addItem(SDItems.COPPER_GOBLET, "铜爵");
        addItem(SDItems.COPPER_DAGGER_AXE, "Copper Dagger-axe", "铜戈");
        addItem(SDItems.COPPER_MIRROR, "铜镜");
        addItem(SDItems.COPPER_MASK, "铜面");
        addItem(SDItems.COPPER_LAMP, "铜镫");
        addItem(SDItems.COPPER_BEAST, "铜兽");
        // suit
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
        // single key
        add(SDLangKeys.SYMBOL_DATE, "Birthday", "生辰八字：");
        add(SDLangKeys.SYMBOL_NAME, "Name", "姓名：");
        add(SDLangKeys.SILKWORM_PLAGUE_TITLE, "Silkworm Plague", "蚕匾");
        add(SDLangKeys.JEI_RECIPE_CHANGE_SOUP, "Change Soup", "变荑汤");
        add(SDLangKeys.JEI_RECIPE_CARVING_TABLE, "Carving Table", "雕刻台");
        add(SDLangKeys.TOP_BIRTHDAY, "Birthday(%s): %s", "生辰八字(%s): %s");
        add(SDLangKeys.TOP_BIRTHDAY_NO, "Birthday: No Record", "生辰八字：无记录");
        add(SDLangKeys.TOP_BLOCK_OWNER, "Owner: %s", "所有者: %s");
        add(SDLangKeys.TOP_BLOCK_ALLOWED, "Allowed Player(%d)", "允许玩家(%d)");

        verify();
    }

    private void verify() {
        for (RegistryObject<Block> entry : SDBlocks.REGISTRY.getEntries()) {
            verify(entry.get().getDescriptionId(), "block", entry.getId());
        }
        for (RegistryObject<Item> entry : SDItems.REGISTRY.getEntries()) {
            verify(entry.get().getDescriptionId(), "item", entry.getId());
        }
        for (String key : SDLangKeys.TRANSLATION_KEYS) {
            verify(key, "other", key);
        }
    }

    private void verify(String languageId, String type, Object name) {
        boolean hasEn = enData.containsKey(languageId) || childEnData.containsKey(languageId);
        boolean hasZh = zhData.containsKey(languageId) || childZhData.containsKey(languageId);
        if (!hasEn && !hasZh) {
            LOGGER.warn("Lost language for {} {}", type, name);
        } else if (!hasEn) {
            LOGGER.warn("Lost english language for {} {}", type, name);
        } else if (!hasZh) {
            LOGGER.warn("Lost chinese language for {} {}", type, name);
        }
    }

    private void addItem(RegistryObject<? extends Item> item, String zhName) {
        String enName = StringUtils.toPascalName(item.getId().getPath(), true);
        super.addItem(item, enName, zhName);
    }

    private void addBlock(RegistryObject<? extends Block> block, String zhName) {
        String enName = StringUtils.toPascalName(block.getId().getPath(), true);
        super.addBlock(block, enName, zhName);
    }
}
