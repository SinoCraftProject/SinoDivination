package games.moegirl.sinocraft.sinodivination.menu;

import games.moegirl.sinocraft.sinocore.api.utility.texture.SlotStrategy;
import games.moegirl.sinocraft.sinocore.api.utility.texture.TextureMap;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.blockentity.SilkwormPlaqueEntity;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class SilkwormPlaqueMenu extends AbstractContainerMenu {

    public static final TextureMap TEXTURE = TextureMap.of(SinoDivination.MOD_ID, "gui", "silkworm_plaque");

    private final SilkwormPlaqueEntity entity;

    public SilkwormPlaqueMenu(int pContainerId, Inventory inventory, SilkwormPlaqueEntity entity) {
        super(SDMenus.SILKWORM_PLAQUE.get(), pContainerId);
        this.entity = entity;

        TEXTURE.placeSlots(inventory, "inventory", 9, this::addSlot, SlotStrategy.noLimit());
        TEXTURE.placeSlots(inventory, "selection", 0, this::addSlot, SlotStrategy.noLimit());
        TEXTURE.placeSlots(entity.in().getInv(), "in", 0, this::addSlot, SlotStrategy.insertFilter(SDItems.SILKWORM_BABY));
        TEXTURE.placeSlots(entity.out().getInv(), "out", 0, this::addSlot, SlotStrategy.onlyTake());
        TEXTURE.placeSlot(entity.feed().getInv(), "feed", 0, this::addSlot, SlotStrategy.onlyTake());
    }

    public SilkwormPlaqueMenu(int pContainerId, Inventory inventory) {
        //noinspection ConstantConditions
        this(pContainerId, inventory, SDBlockEntities.SILKWORM_PLAQUE.get()
                .create(BlockPos.ZERO, SDBlocks.SILKWORM_PLAQUE.get().defaultBlockState()));
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public SilkwormPlaqueEntity entity() {
        return entity;
    }
}
