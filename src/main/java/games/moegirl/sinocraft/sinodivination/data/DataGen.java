package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.data.provider.*;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = SinoDivination.MODID)
public class DataGen {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        if (event.includeClient()) {
            ExistingFileHelper fileHelper = event.getExistingFileHelper();
            SDBlocks.COTINUS_CHEST.get().verifyTexture(fileHelper);
            SDBlocks.SOPHORA_CHEST.get().verifyTexture(fileHelper);
            SDBlocks.JUJUBE_CHEST.get().verifyTexture(fileHelper);
            SDWoodwork.COTINUS.register().verifyTexture(fileHelper);
            SDWoodwork.SOPHORA.register().verifyTexture(fileHelper);
            SDWoodwork.JUJUBE.register().verifyTexture(fileHelper);

            //block/item models, blockstate JSONs, language files, etc.
            event.getGenerator().addProvider(new SDBlockStateProvider(event));
            event.getGenerator().addProvider(new SDItemModelProvider(event));
            event.getGenerator().addProvider(new SDLanguageProviders(event.getGenerator()));
            event.getGenerator().addProvider(new SDLootTableProvider(event.getGenerator()));
            event.getGenerator().addProvider(new SDLzhLanguageProvider(event));
        }
        if (event.includeServer()) {
            SDBlockTagProvider provider;
            //recipes, advancements, tags, etc.
            event.getGenerator().addProvider(new SDRecipeProvider(event.getGenerator()));
            event.getGenerator().addProvider(provider = new SDBlockTagProvider(event));
            event.getGenerator().addProvider(new SDItemTagProvider(event, provider));
        }
    }
}
