package games.moegirl.sinocraft.sinodivination;

import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.entity.SDEntities;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.network.SDNetworks;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.world.SDPlacedFeatures;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("sinodivination")
public class SinoDivination {
    public static final String MOD_ID = "sinodivination";

    public SinoDivination() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        SDNetworks.register();
        SDBlocks.REGISTRY.register(bus);
        SDItems.REGISTRY.register(bus);
        SDTrees.register(bus);
        SDBlockEntities.REGISTRY.register(bus);
        SDPlacedFeatures.REGISTRY.register();
        SDEntities.REGISTRY.register(bus);
    }
}
