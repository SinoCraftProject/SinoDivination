package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinocore.api.item.BaseCreativeTab;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

/**
 * Item's creative tab
 */
public class DivinationTab extends BaseCreativeTab {

    public static final DivinationTab INSTANCE = new DivinationTab();

    DivinationTab() {
        super(SinoDivination.MOD_ID);
    }

    @Nullable
    @Override
    public ItemLike getIcon() {
        return SDItems.ORE_JADE.get();
    }
}
