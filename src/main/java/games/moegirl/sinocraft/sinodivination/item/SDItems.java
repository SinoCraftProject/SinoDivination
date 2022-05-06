package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.util.StringUtils;
import games.moegirl.sinocraft.sinodivination.util.Functions;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SDItems {

    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, SinoDivination.MOD_ID);

    public static final RegistryObject<BlockItem> ORE_JADE = block(SDBlocks.ORE_JADE);

    public static final RegistryObject<BlockItem> ORE_NITER = block(SDBlocks.ORE_NITER);

    public static final RegistryObject<BlockItem> ORE_SULPHUR = block(SDBlocks.ORE_SULPHUR);

    public static final RegistryObject<Item> JADE = simple("jade");

    public static final RegistryObject<Item> NITER = simple("niter");

    public static final RegistryObject<Item> SULPHUR = simple("sulphur");

    public static final RegistryObject<Item> GHOST_KILLING_STICK = simple(GhostKillingStick.class);

    public static final RegistryObject<Item> CHANGING_SEEDING_DECOCTION = simple(ChangingSeedlingDecoction.class);

    public static final RegistryObject<Item> DIVINE_POWER_STICK = simple(DivinePowerStick.class);

    public static final RegistryObject<Item> LIFE_SYMBOL = simple(LifeSymbol.class);

    public static final RegistryObject<Item> JUJUBE = food("jujube", 2);

    public static final RegistryObject<Item> GHOST_GUARD_STICK = simple(GhostGuardStick.class);

    public static final RegistryObject<? extends WoodenChestItem> COTINUS_CHEST = REGISTRY.register("cotinus_chest", () -> WoodenChestItem.create(SDBlocks.COTINUS_CHEST));

    public static final RegistryObject<? extends WoodenChestItem> JUJUBE_CHEST = REGISTRY.register("jujube_chest", () -> WoodenChestItem.create(SDBlocks.JUJUBE_CHEST));

    public static final RegistryObject<? extends WoodenChestItem> SOPHORA_CHEST = REGISTRY.register("sophora_chest", () -> WoodenChestItem.create(SDBlocks.SOPHORA_CHEST));

    public static final RegistryObject<ItemNameBlockItem> ARGY_WORMWOOD_SEED = namedBlock("argy_wormwood_seed", SDBlocks.ARGY_WORMWOOD);

    public static final RegistryObject<Item> ARGY_WORMWOOD_LEAVES = simple(ArgyWormwoodLeaves.class);

    public static final RegistryObject<Item> MOXIBUSTION = simple(Moxibustion.class);

    public static final RegistryObject<ItemNameBlockItem> GARLIC_SEED = namedBlock("garlic_seed", SDBlocks.GARLIC);

    public static final RegistryObject<Item> GARLIC = food("garlic", 2);

    public static final RegistryObject<Item> GLUTINOUS_RISE = food("glutinous_rise", 1);

    public static final RegistryObject<ItemNameBlockItem> GLUTINOUS_RISE_SEED = simple(GlutinousRiseSeed.class);

    // =================================================================================================================

    public static RegistryObject<Item> simple(String name) {
        return REGISTRY.register(name, () -> new Item(new Item.Properties().tab(DivinationTab.INSTANCE)));
    }

    public static RegistryObject<Item> food(String name, int nutrition) {
        return REGISTRY.register(name, () -> new Item(new Item.Properties()
                .food(new FoodProperties.Builder().nutrition(nutrition).build())
                .tab(DivinationTab.INSTANCE)));
    }

    public static <T extends Item> RegistryObject<T> simple(Class<? extends T> aClass) {
        return REGISTRY.register(StringUtils.to_snake_name(aClass.getSimpleName()), Functions.constructor(aClass));
    }

    public static <T extends Block> RegistryObject<ItemNameBlockItem> namedBlock(RegistryObject<T> block) {
        return REGISTRY.register(block.getId().getPath(), () -> new ItemNameBlockItem(block.get(), new Item.Properties().tab(DivinationTab.INSTANCE)));
    }

    public static <T extends Block> RegistryObject<ItemNameBlockItem> namedBlock(String name, RegistryObject<T> block) {
        return REGISTRY.register(name, () -> new ItemNameBlockItem(block.get(), new Item.Properties().tab(DivinationTab.INSTANCE)));
    }

    public static <T extends Block> RegistryObject<BlockItem> block(RegistryObject<T> block) {
        return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(DivinationTab.INSTANCE)));
    }
}
