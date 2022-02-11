package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, SinoDivination.MOD_ID);

    public static final RegistryObject<Item> ORE_JADE = REGISTRY.register("ore_jade", () -> new BlockItem(ModBlocks.ORE_JADE.get(), defaultBuilder()));
    public static final RegistryObject<Item> JADE = REGISTRY.register("jade", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> ORE_SULPHUR = REGISTRY.register("ore_sulphur", () -> new BlockItem(ModBlocks.ORE_SULPHUR.get(), defaultBuilder()));
    public static final RegistryObject<Item> SULPHUR = REGISTRY.register("sulphur", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> ORE_NITER = REGISTRY.register("ore_niter", () -> new BlockItem(ModBlocks.ORE_NITER.get(), defaultBuilder()));
    public static final RegistryObject<Item> NITER = REGISTRY.register("niter", () -> new Item(defaultBuilder()));

    public static CreativeModeTab creativeTab = new CreativeModeTab(SinoDivination.MOD_ID) {
        @NonNull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ORE_JADE.get());
        }
    };

    public static Item.Properties defaultBuilder() {
        return new Item.Properties().tab(creativeTab);
    }
}
