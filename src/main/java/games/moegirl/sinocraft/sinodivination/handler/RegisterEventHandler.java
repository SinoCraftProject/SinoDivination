package games.moegirl.sinocraft.sinodivination.handler;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.capability.BirthdayData;
import games.moegirl.sinocraft.sinodivination.world.DebugPrintPlacement;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterEventHandler {

    public static PlacementModifierType<DebugPrintPlacement> P;

    @SubscribeEvent
    public static void onCapabilityRegister(RegisterCapabilitiesEvent event) {
        event.register(BirthdayData.class);
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        P = Registry.register(Registry.PLACEMENT_MODIFIERS, new ResourceLocation(SinoDivination.MOD_ID, "sino_string"), () -> DebugPrintPlacement.CODEC);
    }
}
