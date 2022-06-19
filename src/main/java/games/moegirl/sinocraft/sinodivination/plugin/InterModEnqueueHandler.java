package games.moegirl.sinocraft.sinodivination.plugin;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.plugin.top.TOPPlugins;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = SinoDivination.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InterModEnqueueHandler {

    @SubscribeEvent
    public static void onEnqueueIMC(InterModEnqueueEvent event) {
        registerIMC("theoneprobe", "getTheOneProbe", TOPPlugins::new);
    }

    private static <T> void registerIMC(String name, String method, Supplier<T> message) {
        if (ModList.get().isLoaded(name)) {
            InterModComms.sendTo(name, method, message);
        }
    }
}
