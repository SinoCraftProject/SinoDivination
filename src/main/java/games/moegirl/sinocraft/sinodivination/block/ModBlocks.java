package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, SinoDivination.MOD_ID);

    public static final RegistryObject<Block> ORE_JADE = REGISTRY.register("ore_jade", () -> new Block(defaultBuilder()));
    public static final RegistryObject<Block> ORE_SULPHUR = REGISTRY.register("ore_sulphur", () -> new Block(defaultBuilder()));
    public static final RegistryObject<Block> ORE_NITER = REGISTRY.register("ore_niter", () -> new Block(defaultBuilder()));

    public static BlockBehaviour.Properties defaultBuilder() {
        return BlockBehaviour.Properties.of(Material.STONE)
                .requiresCorrectToolForDrops()
                .strength(3.0F);
//                .harvestTool(TreeType.PICKAXE)
    }
}
