package games.moegirl.sinocraft.sinodivination.handler;

import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.block.WoodenChest;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.CropBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegisterEventHandler {

    @SubscribeEvent
    public static void onEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        registerChestEntity(event, SDBlocks.COTINUS_CHEST);
        registerChestEntity(event, SDBlocks.JUJUBE_CHEST);
        registerChestEntity(event, SDBlocks.SOPHORA_CHEST);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        SDBlocks.REGISTRY.getEntries().stream()
                .map(Supplier::get)
                .filter(b -> b instanceof CropBlock)
                .forEach(b -> ItemBlockRenderTypes.setRenderLayer(b, RenderType.cutout()));
    }

    private static <T extends WoodenChest> void registerChestEntity(EntityRenderersEvent.RegisterRenderers event, RegistryObject<T> chestObj) {
        T chest = chestObj.get();
        event.registerBlockEntityRenderer(chest.entity(), chest.renderer()::getBlockRenderer);
    }
}
