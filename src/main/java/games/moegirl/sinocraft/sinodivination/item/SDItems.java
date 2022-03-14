package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SDItems {

    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, SinoDivination.MOD_ID);

    public static final RegistryObject<Item> ORE_JADE = REGISTRY.register("ore_jade", () -> new BlockItem(SDBlocks.ORE_JADE.get(), new Item.Properties().tab(DivinationTab.INSTANCE)));

    public static final RegistryObject<Item> ORE_NITER = REGISTRY.register("ore_niter", () -> new BlockItem(SDBlocks.ORE_NITER.get(), new Item.Properties().tab(DivinationTab.INSTANCE)));

    public static final RegistryObject<Item> ORE_SULPHUR = REGISTRY.register("ore_sulphur", () -> new BlockItem(SDBlocks.ORE_SULPHUR.get(), new Item.Properties().tab(DivinationTab.INSTANCE)));

    public static final RegistryObject<Item> JADE = REGISTRY.register("jade", () -> new Item(new Item.Properties().tab(DivinationTab.INSTANCE)));

    public static final RegistryObject<Item> NITER = REGISTRY.register("niter", () -> new Item(new Item.Properties().tab(DivinationTab.INSTANCE)));

    public static final RegistryObject<Item> SULPHUR = REGISTRY.register("sulphur", () -> new Item(new Item.Properties().tab(DivinationTab.INSTANCE)));

    public static final RegistryObject<Item> GHOST_KILLING_STICK = REGISTRY.register("ghost_killing_stick", GhostKillingStick::new);

    public static final RegistryObject<Item> CHANGING_SEEDING_DECOCTION = REGISTRY.register("changing_seeding_decoction", ChangingSeedlingDecoction::new);
}
