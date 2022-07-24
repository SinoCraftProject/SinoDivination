package games.moegirl.sinocraft.sinodivination.client;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SinoDivination.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRenderTypeSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(SDTrees.COTINUS.leaves(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SDTrees.JUJUBE.leaves(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SDTrees.SOPHORA.leaves(), RenderType.cutout());
    }
}
