package games.moegirl.sinocraft.sinodivination.tree;

import games.moegirl.sinocraft.sinocore.api.tree.Tree;
import games.moegirl.sinocraft.sinocore.api.utility.FloatModifier;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.block.JujubeLeaves;
import games.moegirl.sinocraft.sinodivination.item.DivinationTab;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.world.SDPlacedFeatures;
import net.minecraftforge.eventbus.api.IEventBus;

public class SDTrees {

    public static final Tree COTINUS = Tree.builder(SinoDivination.MOD_ID, "cotinus")
            .tab(DivinationTab.INSTANCE)
            .grower(() -> SDPlacedFeatures.COTINUS.get().buildConfigured())
            .build(SDBlocks.REGISTRY, SDItems.REGISTRY);

    public static final Tree JUJUBE = Tree.builder(SinoDivination.MOD_ID, "jujube")
            .tab(DivinationTab.INSTANCE)
            .blockStrengthModifier(new FloatModifier().calcAfter(i -> 2 * i))
            .grower(() -> SDPlacedFeatures.JUJUBE.get().buildConfigured())
            .customLeaves(JujubeLeaves::new)
            .build(SDBlocks.REGISTRY, SDItems.REGISTRY);

    public static final Tree SOPHORA = Tree.builder(SinoDivination.MOD_ID, "sophora")
            .tab(DivinationTab.INSTANCE)
            .grower(() -> SDPlacedFeatures.SOPHORA.get().buildConfigured())
            .build(SDBlocks.REGISTRY, SDItems.REGISTRY);

    public static void register(IEventBus bus) {
        SDWoodwork.register(bus);
    }
}
