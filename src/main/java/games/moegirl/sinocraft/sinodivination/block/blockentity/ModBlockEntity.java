package games.moegirl.sinocraft.sinodivination.block.blockentity;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SinoDivination.MOD_ID);

    public static final RegistryObject<BlockEntityType<PotBlockEntity>> POT_BLOCK_ENTITY = REGISTER.register("pot", () -> BlockEntityType.Builder.of(PotBlockEntity::new, ModBlocks.POT.get()).build(null));
}
