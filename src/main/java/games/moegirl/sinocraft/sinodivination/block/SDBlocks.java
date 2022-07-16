package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.api.utility.Functions;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.base.SimpleCropBlock;
import games.moegirl.sinocraft.sinodivination.block.base.WoodenChest;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.util.StringUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SDBlocks {

    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, SinoDivination.MOD_ID);

    public static final RegistryObject<OreJade> ORE_JADE = simple(OreJade.class);

    public static final RegistryObject<OreNiter> ORE_NITER = simple(OreNiter.class);

    public static final RegistryObject<OreSulphur> ORE_SULPHUR = simple(OreSulphur.class);

    public static final RegistryObject<WoodenChest> COTINUS_CHEST = simple(CotinusChest.class);

    public static final RegistryObject<WoodenChest> JUJUBE_CHEST = simple(JujubeChest.class);

    public static final RegistryObject<WoodenChest> SOPHORA_CHEST = simple(SophoraChest.class);

    public static final RegistryObject<SimpleCropBlock<Item>> WORMWOOD = crop3(() -> SDItems.WORMWOOD_LEAF, "wormwood", 0, 1, 2, 2);

    public static final RegistryObject<SimpleCropBlock<Item>> GARLIC = crop3(() -> SDItems.GARLIC, "garlic", 0, 1, 2, 3);

    public static final RegistryObject<Rice> RICE = simple(Rice.class);

    public static final RegistryObject<LucidGanoderma> LUCID_GANODERMA = simple(LucidGanoderma.class);

    public static final RegistryObject<SimpleCropBlock<Item>> REHMANNIA = crop3(() -> SDItems.REHMANNIA, "rehmannia", 0, 1, 1, 1);

    public static final RegistryObject<SimpleCropBlock<Item>> DRAGONLIVER_MELON = crop(() -> SDItems.DRAGONLIVER_MELON, "dragonliver_melon", 0, 1, 1, 1);

    public static final RegistryObject<SimpleCropBlock<Item>> SESAME = crop3(() -> SDItems.SESAME, "sesame", 0, 1, 3, 3);

    public static final RegistryObject<ZhuCao> ZHU_CAO = simple(ZhuCao.class);

    public static final RegistryObject<BrightStemGrass> BRIGHT_STEM_GRASS = simple(BrightStemGrass.class);

    public static final RegistryObject<Bellows> BELLOWS = simple(Bellows.class);

    public static final RegistryObject<KettlePot> KETTLE_POT = simple(KettlePot.class);

    public static final RegistryObject<SilkwormPlaque> SILKWORM_PLAQUE = simple(SilkwormPlaque.class);

    public static final RegistryObject<Tripod> TRIPOD = simple(Tripod.class);

    public static final RegistryObject<Altar> ALTAR = simple(Altar.class);

    public static final RegistryObject<CarvingTable> CARVING_TABLE = simple(CarvingTable.class);

    // =================================================================================================================

    public static <T extends Block> RegistryObject<T> simple(Class<? extends T> aClass) {
        return block(StringUtils.to_snake_name(aClass.getSimpleName()), Functions.constructor(aClass));
    }

    public static <T extends Item> RegistryObject<SimpleCropBlock<T>> crop(Supplier<RegistryObject<T>> crop, String name, int minSeedCount, int maxSeedCount, int minCropCount, int maxCropCount) {
        return block(name, () -> new SimpleCropBlock<>(crop, minSeedCount, maxSeedCount, minCropCount, maxCropCount));
    }

    public static <T extends Item> RegistryObject<SimpleCropBlock<T>> crop3(Supplier<RegistryObject<T>> crop, String name, int minSeedCount, int maxSeedCount, int minCropCount, int maxCropCount) {
        return block(name, () -> SimpleCropBlock.withAge(crop, 3, minSeedCount, maxSeedCount, minCropCount, maxCropCount));
    }

    public static <T extends Block> RegistryObject<T> block(String name, Supplier<T> supplier) {
        return REGISTRY.register(name, supplier);
    }
}
