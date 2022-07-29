package games.moegirl.sinocraft.sinodivination;

import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.client.screen.SDScreens;
import games.moegirl.sinocraft.sinodivination.command.SDCommands;
import games.moegirl.sinocraft.sinodivination.entity.SDEntities;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.menu.SDMenus;
import games.moegirl.sinocraft.sinodivination.network.SDNetworks;
import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import games.moegirl.sinocraft.sinodivination.world.SDPlacedFeatures;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("sinodivination")
public class SinoDivination {

    public static final String MOD_ID = "sinodivination";
    public static final String NAME = "SinoDivination";
    public static final String MOD_VERSION = "1.0.2";
    public static final String MC_VERSION = "1.18.1";
    public static final String VERSION = MC_VERSION + "-" + MOD_VERSION;

    private final Logger LOGGER = LogManager.getLogger(NAME);

    public SinoDivination() {
        LOGGER.info("Loading SinoDivination. Ver: " + VERSION);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::onClientSetup);
        SDNetworks.register();
        SDBlocks.REGISTRY.register(bus);
        SDItems.REGISTRY.register(bus);
        SDBlockEntities.REGISTRY.register(bus);
        SDPlacedFeatures.REGISTRY.register();
        SDEntities.REGISTRY.register(bus);
        SDTrees.register(bus);
        SDMenus.REGISTRY.register(bus);
        SDRecipes.REGISTRY.register(bus);
        SDScreens.register(bus);
        SDCommands.REGISTER.register();

        LOGGER.info("Reverence for heaven and earth, respect ghosts and gods.");
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            SDWoodwork.JUJUBE.register().registerRender();
            SDWoodwork.COTINUS.register().registerRender();
            SDWoodwork.SOPHORA.register().registerRender();

            SDTrees.JUJUBE.register().registerRenderType();
            SDTrees.COTINUS.register().registerRenderType();
            SDTrees.SOPHORA.register().registerRenderType();
        });
    }
}
