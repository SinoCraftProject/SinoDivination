package games.moegirl.sinocraft.sinodivination.tree;

import games.moegirl.sinocraft.sinocore.api.woodwork.NetworkHolder;
import games.moegirl.sinocraft.sinocore.api.woodwork.Woodwork;
import games.moegirl.sinocraft.sinocore.api.woodwork.WoodworkManager;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.*;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.network.SDNetworks;
import net.minecraftforge.eventbus.api.IEventBus;

public class SDWoodwork {

    public static final WoodworkManager WOODWORK = new WoodworkManager(SinoDivination.MOD_ID,
            SDItems.REGISTRY, SDBlocks.REGISTRY,
            SDBlockEntities.REGISTRY, NetworkHolder.simple(SDNetworks.CHANNEL, SDNetworks.ID));

    public static final Woodwork COTINUS = Woodwork.builder("cotinus")
            .customDoor((properties, __) -> new CotinusDoor(properties))
            .customTrapdoor((properties, __) -> new CotinusTrapdoor(properties))
            .customFenceGate((properties, __) -> new CotinusFenceGate(properties))
            .build(WOODWORK);

    public static final Woodwork JUJUBE = Woodwork.builder("jujube")
            .customStrengthModifier(modifier -> modifier.calcAfter(f -> 2 * f))
            .customDoor(JujubeDoor::new)
            .customTrapdoor(JujubeTrapdoor::new)
            .customFenceGate(JujubeFenceGate::new)
            .build(WOODWORK);

    public static final Woodwork SOPHORA = Woodwork.builder("sophora")
            .customDoor((properties, __) -> new SophoraDoor(properties))
            .customTrapdoor((properties, __) -> new SophoraTrapdoor(properties))
            .customFenceGate((properties, __) -> new SophoraFenceGate(properties))
            .build(WOODWORK);

    static void register(IEventBus bus) {
        COTINUS.register().register(bus);
        JUJUBE.register().register(bus);
        SOPHORA.register().register(bus);
    }
}
