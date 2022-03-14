package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = SinoDivination.MOD_ID)
public class DataGenRegister {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        if (event.includeClient()) {
            //block/item models, blockstate JSONs, language files, etc.
            event.getGenerator().addProvider(new SDBlockStateProvider(event));
            event.getGenerator().addProvider(new SDItemModelProvider(event));
            event.getGenerator().addProvider(new SDLanguageProviders(event.getGenerator()));
            event.getGenerator().addProvider(new SDLootTableProvider(event.getGenerator()));
            event.getGenerator().addProvider(new SDLzhLanguageProvider(event));
        }
        if (event.includeServer()) {
            //recipes, advancements, tags, etc.
            event.getGenerator().addProvider(new SDRecipeProvider(event.getGenerator()));
            SDBlockTagProvider provider = new SDBlockTagProvider(event);
            event.getGenerator().addProvider(provider);
            event.getGenerator().addProvider(new SDItemTagProvider(event, provider));
        }
    }
}
