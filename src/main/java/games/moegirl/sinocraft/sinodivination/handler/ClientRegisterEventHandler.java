package games.moegirl.sinocraft.sinodivination.handler;

import games.moegirl.sinocraft.sinocore.api.client.WoodworkClientRegister;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.block.base.Crop;
import games.moegirl.sinocraft.sinodivination.block.base.WoodenChest;
import games.moegirl.sinocraft.sinodivination.blockentity.AltarEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.client.AltarRenderer;
import games.moegirl.sinocraft.sinodivination.client.KettlePotRenderer;
import games.moegirl.sinocraft.sinodivination.client.TripodRenderer;
import games.moegirl.sinocraft.sinodivination.client.WoodenChestRenderer;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegisterEventHandler {

    @SubscribeEvent
    public static void onEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        registerChestEntity(event, SDBlocks.COTINUS_CHEST);
        registerChestEntity(event, SDBlocks.JUJUBE_CHEST);
        registerChestEntity(event, SDBlocks.SOPHORA_CHEST);
        event.registerBlockEntityRenderer(SDBlockEntities.TRIPOD.get(), TripodRenderer::new);
        event.registerBlockEntityRenderer(SDBlockEntities.ALTAR.get(), AltarRenderer::new);
        event.registerBlockEntityRenderer(SDBlockEntities.KETTLE_POT.get(), KettlePotRenderer::new);
        for (Object o : SDWoodwork.CLIENT) {
            WoodworkClientRegister register = (WoodworkClientRegister) o;
            register.registerRenderer(event);
        }
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {

        SDBlocks.REGISTRY.getEntries().stream()
                .map(Supplier::get)
                .filter(b -> b instanceof Crop<?>)
                .forEach(b -> ItemBlockRenderTypes.setRenderLayer(b, RenderType.cutout()));
    }

    @SubscribeEvent
    public static void onBlockColor(ColorHandlerEvent.Block event) {
        Map<Direction, Integer> altarColorMap = new Object2IntArrayMap<>();
        altarColorMap.put(Direction.EAST, Color.CYAN.getRGB());
        altarColorMap.put(Direction.WEST, Color.WHITE.getRGB());
        altarColorMap.put(Direction.NORTH, Color.BLACK.getRGB());
        altarColorMap.put(Direction.SOUTH, Color.RED.getRGB());
        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) -> {
            if (pLevel == null || pPos == null) return 0;
            return pLevel.getBlockEntity(pPos, SDBlockEntities.ALTAR.get())
                    .flatMap(AltarEntity::getDirection)
                    .map(altarColorMap::get)
                    .orElse(0);
        }, SDBlocks.ALTAR.get());
    }

    private static <T extends WoodenChest> void registerChestEntity(EntityRenderersEvent.RegisterRenderers event, RegistryObject<T> chestObj) {
        T chest = chestObj.get();
        event.registerBlockEntityRenderer(chest.entity(), WoodenChestRenderer.get(chest)::getBlockRenderer);
    }
}
