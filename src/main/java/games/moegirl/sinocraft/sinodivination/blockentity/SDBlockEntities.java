package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SDBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> REGISTER =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SinoDivination.MOD_ID);

    public static final RegistryObject<BlockEntityType<?>> COTINUS_DOOR = REGISTER.register("cotinus_door",
            () -> buildEntity(CotinusDoorBEntity::new, SDTrees.COTINUS.getBlocks().door));

    public static final RegistryObject<BlockEntityType<?>> COTINUS_TRAPDOOR = REGISTER.register("cotinus_trapdoor",
            () -> buildEntity(CotinusTrapdoorBEntity::new, SDTrees.COTINUS.getBlocks().trapdoor));

    public static final RegistryObject<BlockEntityType<?>> COTINUS_FENCE_GATE = REGISTER.register("cotinus_fence_gate",
            () -> buildEntity(CotinusFenceGateBEntity::new, SDTrees.COTINUS.getBlocks().fenceGate));

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
    }

    private static <T extends BlockEntity> BlockEntityType<T> buildEntity(BlockEntityType.BlockEntitySupplier<T> supplier,
                                                                          RegistryObject<? extends Block> block) {
        return BlockEntityType.Builder.of(supplier, block.get()).build(null);
    }
}
