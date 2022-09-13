package games.moegirl.sinocraft.sinodivination.tree;

import games.moegirl.sinocraft.sinocore.api.client.WoodworkClientRegister;
import games.moegirl.sinocraft.sinocore.api.woodwork.NetworkHolder;
import games.moegirl.sinocraft.sinocore.api.woodwork.Woodwork;
import games.moegirl.sinocraft.sinocore.api.woodwork.WoodworkManager;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.*;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.item.DivinationTab;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.network.SDNetworks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SDWoodwork {

    public static final WoodworkManager WOODWORK = new WoodworkManager(SinoDivination.MOD_ID,
            SDItems.REGISTRY, SDBlocks.REGISTRY,
            SDBlockEntities.REGISTRY, NetworkHolder.simple(SDNetworks.CHANNEL, SDNetworks.ID));

    public static final Woodwork COTINUS = Woodwork.builder("cotinus")
            .defaultTab(DivinationTab.INSTANCE)
            .customDoor((properties, __) -> new CotinusDoor(properties))
            .customTrapdoor((properties, __) -> new CotinusTrapdoor(properties))
            .customFenceGate((properties, __) -> new CotinusFenceGate(properties))
            .build(WOODWORK);

    public static final Woodwork JUJUBE = Woodwork.builder("jujube")
            .defaultTab(DivinationTab.INSTANCE)
            .customStrengthModifier(modifier -> modifier.calcAfter(f -> 2 * f))
            .customDoor(JujubeDoor::new)
            .customTrapdoor(JujubeTrapdoor::new)
            .customFenceGate(JujubeFenceGate::new)
            .build(WOODWORK);

    public static final Woodwork SOPHORA = Woodwork.builder("sophora")
            .defaultTab(DivinationTab.INSTANCE)
            .customDoor((properties, __) -> new SophoraDoor(properties))
            .customTrapdoor((properties, __) -> new SophoraTrapdoor(properties))
            .customFenceGate((properties, __) -> new SophoraFenceGate(properties))
            .build(WOODWORK);

    public static final List<Object> CLIENT = new ArrayList<>(WOODWORK.allNames().size());

    public static void register(IEventBus bus) {
        bus.addListener((Consumer<FMLClientSetupEvent>) event -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            WOODWORK.forEach(woodwork -> {
                WoodworkClientRegister register = new WoodworkClientRegister(woodwork);
                CLIENT.add(register);
                register.registerRender();
            });
        }));
    }
}
