package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.util.StringUtils;
import games.moegirl.sinocraft.sinodivination.util.Functions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SDBlocks {

    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, SinoDivination.MOD_ID);

    public static final RegistryObject<JadeOreBlock> ORE_JADE = simple("ore_jade", JadeOreBlock.class);

    public static final RegistryObject<OreBlock> ORE_NITER = ore("ore_niter");

    public static final RegistryObject<OreBlock> ORE_SULPHUR = ore("ore_sulphur");

    public static final RegistryObject<WoodenChest> COTINUS_CHEST = simple(CotinusChest.class);

    public static final RegistryObject<WoodenChest> JUJUBE_CHEST = simple(JujubeChest.class);

    public static final RegistryObject<WoodenChest> SOPHORA_CHEST = simple(SophoraChest.class);

    public static final RegistryObject<CropBlock> ARGY_WORMWOOD = simple(ArgyWormwood.class);

    public static final RegistryObject<CropBlock> GARLIC = simple(Garlic.class);

    public static final RegistryObject<GlutinousRise> GLUTINOUS_RISE = simple(GlutinousRise.class);

    // =================================================================================================================

    public static RegistryObject<OreBlock> ore(String name) {
        return REGISTRY.register(name, () -> new OreBlock(Material.STONE));
    }

    public static <T extends Block> RegistryObject<T> simple(String name, Class<? extends T> aClass) {
        return REGISTRY.register(name, Functions.constructor(aClass));
    }

    public static <T extends Block> RegistryObject<T> simple(Class<? extends T> aClass) {
        return REGISTRY.register(StringUtils.to_snake_name(aClass.getSimpleName()), Functions.constructor(aClass));
    }
}
