package games.moegirl.sinocraft.sinodivination;

import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.world.SDPlacedFeatures;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("sinodivination")
public class SinoDivination {
    public static final String MOD_ID = "sinodivination";
    private static final Logger LOGGER = LogManager.getLogger();

    public SinoDivination() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        SDBlocks.REGISTRY.register(bus);
        SDItems.REGISTRY.register(bus);
        SDTrees.REGISTER.register(bus);
        SDTrees.REGISTER.register(bus);
        SDBlockEntities.register(bus);
        SDPlacedFeatures.register();
    }
}
