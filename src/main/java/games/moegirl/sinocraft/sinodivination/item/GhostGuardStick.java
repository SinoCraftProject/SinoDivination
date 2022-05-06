package games.moegirl.sinocraft.sinodivination.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class GhostGuardStick extends SwordItem {

    public GhostGuardStick() {
        super(Tiers.WOOD, 6, -2.4F, new Properties()
                .durability(Tiers.WOOD.getUses() * 2)
                .tab(DivinationTab.INSTANCE));
    }
}
