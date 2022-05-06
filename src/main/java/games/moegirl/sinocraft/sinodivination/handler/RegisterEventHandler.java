package games.moegirl.sinocraft.sinodivination.handler;

import games.moegirl.sinocraft.sinodivination.capability.BirthdayData;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterEventHandler {

    @SubscribeEvent
    public static void onCapabilityRegister(RegisterCapabilitiesEvent event) {
        event.register(BirthdayData.class);
    }
}
