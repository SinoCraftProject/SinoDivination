package games.moegirl.sinocraft.sinodivination.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class GhostKillingStick extends SwordItem {

    public GhostKillingStick() {
        super(Tiers.WOOD, 6, -2.4F, new Properties().tab(DivinationTab.INSTANCE));
    }
}
