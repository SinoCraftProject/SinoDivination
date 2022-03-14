package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SDBlocks {

    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, SinoDivination.MOD_ID);

    public static final RegistryObject<Block> ORE_JADE = REGISTRY.register("ore_jade", () -> new SDOreBlock(Material.STONE));

    public static final RegistryObject<Block> ORE_NITER = REGISTRY.register("ore_niter", () -> new SDOreBlock(Material.STONE));

    public static final RegistryObject<Block> ORE_SULPHUR = REGISTRY.register("ore_sulphur", () -> new SDOreBlock(Material.STONE));
}
