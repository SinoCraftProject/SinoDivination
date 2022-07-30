package games.moegirl.sinocraft.sinodivination.menu;

import games.moegirl.sinocraft.sinocore.api.utility.texture.SlotStrategy;
import games.moegirl.sinocraft.sinocore.api.utility.texture.TextureMap;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.recipe.CarvingTableRecipeContainer;
import games.moegirl.sinocraft.sinodivination.data.SDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.common.Tags;

public class CarvingTableMenu extends AbstractContainerMenu {

    public static final TextureMap TEXTURE = TextureMap.of(SinoDivination.MOD_ID, "gui", "carving_table");

    public BlockPos pos = BlockPos.ZERO;
    private final CarvingTableRecipeContainer container = new CarvingTableRecipeContainer();

    protected CarvingTableMenu(int pContainerId, Inventory inventory) {
        super(SDMenus.CARVING_TABLE.get(), pContainerId);

        TEXTURE.placeSlots(inventory, "inventory", 9, this::addSlot, SlotStrategy.noLimit());
        TEXTURE.placeSlots(inventory, "selection", 0, this::addSlot, SlotStrategy.noLimit());
        TEXTURE.placeSlots(container, "input", 0, this::addSlot, SlotStrategy.insertFilter(SDTags.SACRIFICIAL_UTENSIL_MATERIAL));
        TEXTURE.placeSlot(container, "dye", 16, this::addSlot, SlotStrategy.insertFilter(Tags.Items.DYES));
        TEXTURE.placeSlot(container, "output", 17, this::addSlot, SlotStrategy.onlyTake());
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0;
    }
}
