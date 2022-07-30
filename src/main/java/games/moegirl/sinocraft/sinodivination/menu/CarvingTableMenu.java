package games.moegirl.sinocraft.sinodivination.menu;

import games.moegirl.sinocraft.sinocore.api.utility.texture.SlotStrategy;
import games.moegirl.sinocraft.sinocore.api.utility.texture.TextureMap;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.recipe.CarvingTableRecipeContainer;
import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import games.moegirl.sinocraft.sinodivination.data.SDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;

public class CarvingTableMenu extends AbstractContainerMenu {

    public static final TextureMap TEXTURE = TextureMap.of(SinoDivination.MOD_ID, "gui", "carving_table");

    public BlockPos pos = BlockPos.ZERO;
    @Nullable
    public Player owner = null;
    private final CarvingTableRecipeContainer container = new CarvingTableRecipeContainer(this);

    public CarvingTableMenu(int pContainerId, Inventory inventory) {
        super(SDMenus.CARVING_TABLE.get(), pContainerId);

        TEXTURE.placeSlots(inventory, "inventory", 9, this::addSlot, SlotStrategy.noLimit());
        TEXTURE.placeSlots(inventory, "selection", 0, this::addSlot, SlotStrategy.noLimit());
        TEXTURE.placeSlots(container, "input", 0, this::addSlot, SlotStrategy.insertFilter(SDTags.SACRIFICIAL_UTENSIL_MATERIAL));
        TEXTURE.placeSlot(container, "dye", 16, this::addSlot, SlotStrategy.insertFilter(Tags.Items.DYES));
        TEXTURE.placeSlot(container, "output", 17, this::addSlot, (container, slot, x, y) -> new Slot(container, slot, x, y) {
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                super.onTake(player, stack);
                container.clearContent();
            }
        });
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void slotsChanged(Container inventory) {
        if (owner != null) {
            SDRecipes.CARVING_TABLE.match(owner.level, container)
                    .ifPresent(recipe -> container.setOutput(recipe.assemble(container)));
        }
        super.slotsChanged(inventory);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (player instanceof ServerPlayer sp) {
            if (player.isAlive() && !sp.hasDisconnected()) {
                container.forInputs(player.getInventory()::placeItemBackInInventory);
            } else {
                container.forInputs(is -> player.drop(is, false));
            }
            container.clearContent();
        }
    }
}
