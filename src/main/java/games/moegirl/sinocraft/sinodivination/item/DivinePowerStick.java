package games.moegirl.sinocraft.sinodivination.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class DivinePowerStick extends SwordItem {

    public DivinePowerStick() {
        super(Tiers.IRON, 3, -2.4f, new Properties()
                .durability(Tiers.IRON.getUses() * 2)
                .tab(DivinationTab.INSTANCE));
    }
}
