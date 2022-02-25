package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinocore.api.data.gen.DefaultItemModelProvider;
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
            event.getGenerator().addProvider(new BlockStateProvider(event.getGenerator(),
                    SinoDivination.MOD_ID, event.getExistingFileHelper()));
            event.getGenerator().addProvider(new DefaultItemModelProvider(event.getGenerator(),
                    SinoDivination.MOD_ID, event.getExistingFileHelper()));
        }
        if (event.includeServer()) {
            //recipes, advancements, tags, etc.
        }
    }
}
