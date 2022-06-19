package games.moegirl.sinocraft.sinodivination.util.texture;

import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Predicate;

public interface SlotStrategy {

    static SlotStrategy simple() {
        return (container, slot, x, y) -> new Slot(container, slot, x, y) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                for (int i = 0; i < container.getContainerSize(); i++) {
                    if (container.canPlaceItem(i, pStack)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    static SlotStrategy noLimit() {
        return Slot::new;
    }

    static SlotStrategy onlyInsert() {
        return (container, slot, x, y) -> new Slot(container, slot, x, y) {
            @Override
            public boolean mayPickup(Player pPlayer) {
                return false;
            }
        };
    }

    static SlotStrategy onlyTake() {
        return (container, slot, x, y) -> new Slot(container, slot, x, y) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }
        };
    }

    static SlotStrategy insertFilter(Predicate<ItemStack> test) {
        return (container, slot, x, y) -> new Slot(container, slot, x, y) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return test.test(pStack);
            }
        };
    }

    static SlotStrategy insertFilter(ItemLike item) {
        return insertFilter(is -> is.is(item.asItem()));
    }

    static SlotStrategy insertFilter(TagKey<Item> item) {
        return insertFilter(is -> is.is(item));
    }

    static SlotStrategy insertFilter(RegistryObject<? extends ItemLike> item) {
        return insertFilter(is -> is.is(item.get().asItem()));
    }

    Slot createSlot(Container container, int slot, int x, int y);
}
