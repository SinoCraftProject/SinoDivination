package games.moegirl.sinocraft.sinodivination.menu;

import com.google.common.base.Suppliers;
import games.moegirl.sinocraft.sinocore.api.utility.texture.SlotStrategy;
import games.moegirl.sinocraft.sinocore.api.utility.texture.TextureMap;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.blockentity.SilkwormPlaqueEntity;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.function.Supplier;

public class SilkwormPlaqueMenu extends AbstractContainerMenu {

    public static final TextureMap TEXTURE = TextureMap.of(SinoDivination.MODID, "gui", "silkworm_plaque");

    private final Supplier<SilkwormPlaqueEntity> entity;

    public SilkwormPlaqueMenu(int pContainerId, Inventory inventory, SilkwormPlaqueEntity entity) {
        this(pContainerId, inventory, Suppliers.memoize(() -> entity));
    }

    public SilkwormPlaqueMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buffer) {
        this(pContainerId, inventory, new Supplier<>() {
            final BlockPos pos = buffer.readBlockPos();

            @Override
            public SilkwormPlaqueEntity get() {
                return (SilkwormPlaqueEntity) Minecraft.getInstance().level.getBlockEntity(pos);
            }
        });
    }

    private SilkwormPlaqueMenu(int pContainerId, Inventory inventory, Supplier<SilkwormPlaqueEntity> entity) {
        super(SDMenus.SILKWORM_PLAQUE.get(), pContainerId);
        this.entity = entity;

        TEXTURE.placeSlots(inventory, "inventory", 9, this::addSlot, SlotStrategy.noLimit());
        TEXTURE.placeSlots(inventory, "selection", 0, this::addSlot, SlotStrategy.noLimit());
        TEXTURE.placeSlot(entity.get().in().getInv(), "in0", 0, this::addSlot, SlotStrategy.insertFilter(SDItems.SILKWORM_BABY));
        TEXTURE.placeSlot(entity.get().in().getInv(), "in1", 1, this::addSlot, SlotStrategy.insertFilter(SDItems.SILKWORM_BABY));
        TEXTURE.placeSlot(entity.get().in().getInv(), "in2", 2, this::addSlot, SlotStrategy.insertFilter(SDItems.SILKWORM_BABY));
        TEXTURE.placeSlot(entity.get().in().getInv(), "in3", 3, this::addSlot, SlotStrategy.insertFilter(SDItems.SILKWORM_BABY));
        TEXTURE.placeSlot(entity.get().in().getInv(), "in4", 4, this::addSlot, SlotStrategy.insertFilter(SDItems.SILKWORM_BABY));
        TEXTURE.placeSlot(entity.get().in().getInv(), "in5", 5, this::addSlot, SlotStrategy.insertFilter(SDItems.SILKWORM_BABY));
        TEXTURE.placeSlot(entity.get().in().getInv(), "in6", 6, this::addSlot, SlotStrategy.insertFilter(SDItems.SILKWORM_BABY));
        TEXTURE.placeSlot(entity.get().in().getInv(), "in7", 7, this::addSlot, SlotStrategy.insertFilter(SDItems.SILKWORM_BABY));
        TEXTURE.placeSlot(entity.get().out().getInv(), "out", 0, this::addSlot, SlotStrategy.onlyTake());
        TEXTURE.placeSlot(entity.get().feed().getInv(), "feed", 0, this::addSlot, SlotStrategy.insertFilter(ItemTags.LEAVES));
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public SilkwormPlaqueEntity entity() {
        return entity.get();
    }
}
