package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * Item's creative tab
 */
public class DivinationTab extends CreativeModeTab {

    public static final DivinationTab INSTANCE = new DivinationTab();

    DivinationTab() {
        super(SinoDivination.MODID);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(SDItems.ORE_JADE.get());
    }
}
