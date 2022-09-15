package games.moegirl.sinocraft.sinodivination.blockentity;

import com.mojang.datafixers.util.Function3;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.block.base.WoodenChest;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

import static games.moegirl.sinocraft.sinodivination.tree.SDWoodwork.COTINUS;
import static games.moegirl.sinocraft.sinodivination.tree.SDWoodwork.SOPHORA;

public class SDBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SinoDivination.MODID);

    public static final RegistryObject<BlockEntityType<CotinusDoorEntity>> COTINUS_DOOR = simple(CotinusDoorEntity::new, COTINUS.door);

    public static final RegistryObject<BlockEntityType<CotinusEntity>> COTINUS_ENTITY = entity("cotinus_entity", CotinusEntity::new, COTINUS.trapdoor, COTINUS.fenceGate);

    public static final RegistryObject<BlockEntityType<SophoraDoorEntity>> SOPHORA_DOOR = simple(SophoraDoorEntity::new, SOPHORA.door);

    public static final RegistryObject<BlockEntityType<SophoraEntity>> SOPHORA_ENTITY = entity("sophora_entity", SophoraEntity::new, SOPHORA.trapdoor, SOPHORA.fenceGate);

    public static final RegistryObject<BlockEntityType<? extends WoodenChestEntity>> COTINUS_CHEST = chest(SDBlocks.COTINUS_CHEST, CotinusChestEntity::new);

    public static final RegistryObject<BlockEntityType<? extends WoodenChestEntity>> JUJUBE_CHEST = chest(SDBlocks.JUJUBE_CHEST, JujubeChestEntity::new);

    public static final RegistryObject<BlockEntityType<? extends WoodenChestEntity>> SOPHORA_CHEST = chest(SDBlocks.SOPHORA_CHEST, SophoraChestEntity::new);

    public static final RegistryObject<BlockEntityType<SilkwormPlaqueEntity>> SILKWORM_PLAQUE = simple(SilkwormPlaqueEntity::new, SDBlocks.SILKWORM_PLAQUE);

    public static final RegistryObject<BlockEntityType<TripodEntity>> TRIPOD = simple(TripodEntity::new, SDBlocks.TRIPOD);

    public static final RegistryObject<BlockEntityType<AltarEntity>> ALTAR = simple(AltarEntity::new, SDBlocks.ALTAR);

    public static final RegistryObject<BlockEntityType<KettlePotEntity>> KETTLE_POT = simple(KettlePotEntity::new, SDBlocks.KETTLE_POT);

    // =================================================================================================================

    public static Optional<KettlePotEntity> getKettlePot(LevelReader level, BlockPos pos) {
        return getEntity(level, pos, KETTLE_POT);
    }

    public static <T extends BlockEntity> Optional<T> getEntity(LevelReader level, BlockPos pos,
                                                                RegistryObject<BlockEntityType<T>> type) {
        if (level.isClientSide() || !level.isAreaLoaded(pos, 1)) return Optional.empty();
        return level.getBlockEntity(pos, type.get());
    }

    // =================================================================================================================

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> simple(Function3<BlockEntityType<?>, BlockPos, BlockState, T> supplier, RegistryObject<? extends Block> block) {
        return entity(block.getId().getPath() + "_entity", supplier, block);
    }

    @SuppressWarnings("ConstantConditions")
    private static <T extends WoodenChestEntity> RegistryObject<BlockEntityType<? extends WoodenChestEntity>> chest(RegistryObject<? extends WoodenChest> type, BlockEntityType.BlockEntitySupplier<T> supplier) {
        return REGISTRY.register(type.getId().getPath(), () -> BlockEntityType.Builder.of(supplier, type.get()).build(null));
    }

    @SuppressWarnings({"rawtypes", "ConstantConditions"})
    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> entity(String name, Function3<BlockEntityType<?>, BlockPos, BlockState, T> supplier, RegistryObject... blocks) {
        Holder<T> holder = new Holder<>();
        holder.type = REGISTRY.register(name, () -> {
            Block[] bs = new Block[blocks.length];
            for (int i = 0; i < blocks.length; i++) {
                bs[i] = (Block) blocks[i].get();
            }
            return BlockEntityType.Builder.of((pos, state) ->
                    supplier.apply(holder.type.get(), pos, state), bs).build(null);
        });
        return holder.type;
    }

    static class Holder<T extends BlockEntity> {
        @SuppressWarnings("NotNullFieldNotInitialized")
        RegistryObject<BlockEntityType<T>> type;
    }
}
