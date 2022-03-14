package games.moegirl.sinocraft.sinodivination.tree;

import games.moegirl.sinocraft.sinocore.api.tree.Tree;
import games.moegirl.sinocraft.sinocore.api.tree.TreeRegister;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.CotinusDoorBlock;
import games.moegirl.sinocraft.sinodivination.block.CotinusFenceGateBlock;
import games.moegirl.sinocraft.sinodivination.block.CotinusTrapdoorBlock;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.item.DivinationTab;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.world.SDPlacedFeatures;

public class SDTrees {

    public static final TreeRegister REGISTER = new TreeRegister(DivinationTab.INSTANCE, SDItems.REGISTRY, SDBlocks.REGISTRY);

    public static final Tree COTINUS = REGISTER.register("无患", Tree.builder(SinoDivination.MOD_ID, "cotinus")
            .customDoor(CotinusDoorBlock::new)
            .customTrapDoor(CotinusTrapdoorBlock::new)
            .customFenceGate(CotinusFenceGateBlock::new)
            .grower(() -> SDPlacedFeatures.COTINUS.get().configured())
            .build());

    public static final Tree JUJUBE = REGISTER.register("枣", Tree.builder(SinoDivination.MOD_ID, "jujube")
            .build());

    public static final Tree SOPHORA = REGISTER.register("槐", Tree.builder(SinoDivination.MOD_ID, "sophora")
            .build());
}
