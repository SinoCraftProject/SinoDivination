package games.moegirl.sinocraft.sinodivination.blockentity;

import com.mojang.datafixers.util.Function3;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.block.WoodenChest;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static games.moegirl.sinocraft.sinodivination.tree.SDWoodwork.COTINUS;
import static games.moegirl.sinocraft.sinodivination.tree.SDWoodwork.SOPHORA;

public class SDBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SinoDivination.MOD_ID);

    public static final RegistryObject<BlockEntityType<?>> COTINUS_DOOR = entity("cotinus_door", CotinusDoorEntity::new, COTINUS.door);

    public static final RegistryObject<BlockEntityType<?>> COTINUS_ENTITY = entity("cotinus_entity", CotinusEntity::new, COTINUS.trapdoor, COTINUS.fenceGate);

    public static final RegistryObject<BlockEntityType<?>> SOPHORA_DOOR = entity("sophora_door", SophoraDoorEntity::new, SOPHORA.door);

    public static final RegistryObject<BlockEntityType<?>> SOPHORA_ENTITY = entity("sophora_entity", SophoraEntity::new, SOPHORA.trapdoor, SOPHORA.fenceGate);

    public static final RegistryObject<BlockEntityType<? extends WoodenChestEntity>> COTINUS_CHEST = chest(SDBlocks.COTINUS_CHEST, CotinusChestEntity::new);

    public static final RegistryObject<BlockEntityType<? extends WoodenChestEntity>> JUJUBE_CHEST = chest(SDBlocks.JUJUBE_CHEST, JujubeChestEntity::new);

    public static final RegistryObject<BlockEntityType<? extends WoodenChestEntity>> SOPHORA_CHEST = chest(SDBlocks.SOPHORA_CHEST, SophoraChestEntity::new);

    // =================================================================================================================

    @SuppressWarnings({"rawtypes", "ConstantConditions"})
    private static <T extends BlockEntity> RegistryObject<BlockEntityType<?>> entity(String name, Function3<BlockEntityType<?>, BlockPos, BlockState, T> supplier, RegistryObject... blocks) {
        Holder holder = new Holder();
        Block[] bs = new Block[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            bs[i] = (Block) blocks[i].get();
        }
        holder.type = REGISTRY.register(name, () -> BlockEntityType.Builder.of((pos, state) ->
                supplier.apply(holder.type.get(), pos, state), bs).build(null));
        return holder.type;
    }

    @SuppressWarnings("ConstantConditions")
    private static <T extends WoodenChestEntity> RegistryObject<BlockEntityType<? extends WoodenChestEntity>> chest(RegistryObject<? extends WoodenChest> type, BlockEntityType.BlockEntitySupplier<T> supplier) {
        return REGISTRY.register(type.getId().getPath(), () -> BlockEntityType.Builder.of(supplier, type.get()).build(null));
    }

    static class Holder {
        @SuppressWarnings("NotNullFieldNotInitialized")
        RegistryObject<BlockEntityType<?>> type;
    }
}
