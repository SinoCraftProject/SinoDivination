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
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SinoDivination.MODID)
public class SinoDivination {
    public static final String MODID = "sinodivination";
    public static final String NAME = "SinoDivination";
    public static final String VERSION = "@version@";

    private static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public SinoDivination() {
        LOGGER.info("Loading SinoDivination. Ver: " + VERSION);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        SDNetworks.register();
        SDBlocks.REGISTRY.register(bus);
        SDItems.REGISTRY.register(bus);
        SDBlockEntities.REGISTRY.register(bus);
        SDPlacedFeatures.REGISTRY.register();
        SDEntities.REGISTRY.register(bus);
        SDTrees.register(bus);
        SDWoodwork.register(bus);
        SDMenus.REGISTRY.register(bus);
        SDRecipes.REGISTRY.register(bus);
        SDScreens.register(bus);
        SDCommands.REGISTER.register();

        LOGGER.info("Reverence for heaven and earth, respect ghosts and gods.");
    }
}
