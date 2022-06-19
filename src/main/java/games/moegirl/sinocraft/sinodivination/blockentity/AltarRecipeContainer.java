package games.moegirl.sinocraft.sinodivination.blockentity;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public interface AltarRecipeContainer extends Container {

    Item sacrificialVessel(int i);

    ItemStack base();

    static AltarRecipeContainer of(Item sacrificialVessel0, Item sacrificialVessel1, Item sacrificialVessel2, Item sacrificialVessel3, ItemStack base) {
        return new AltarRecipeContainer() {

            final Item[] sacrificialVessels = new Item[]{sacrificialVessel0, sacrificialVessel1, sacrificialVessel2, sacrificialVessel3};
            final ItemStack t = base.copy();

            @Override
            public Item sacrificialVessel(int i) {
                return sacrificialVessels[i];
            }

            @Override
            public ItemStack base() {
                return t;
            }

            @Override
            public void clearContent() {
            }

            @Override
            public int getContainerSize() {
                return 5;
            }

            @Override
            public boolean isEmpty() {
                return true;
            }

            @Override
            public ItemStack getItem(int pIndex) {
                return ItemStack.EMPTY;
            }

            @Override
            public ItemStack removeItem(int pIndex, int pCount) {
                return ItemStack.EMPTY;
            }

            @Override
            public ItemStack removeItemNoUpdate(int pIndex) {
                return ItemStack.EMPTY;
            }

            @Override
            public void setItem(int pIndex, ItemStack pStack) {
            }

            @Override
            public int getMaxStackSize() {
                return 64;
            }

            @Override
            public void setChanged() {
            }

            @Override
            public boolean stillValid(Player pPlayer) {
                return true;
            }

            @Override
            public void startOpen(Player pPlayer) {
            }

            @Override
            public void stopOpen(Player pPlayer) {
            }

            @Override
            public boolean canPlaceItem(int pIndex, ItemStack pStack) {
                return false;
            }

            @Override
            public int countItem(Item pItem) {
                return 0;
            }

            @Override
            public boolean hasAnyOf(Set<Item> pSet) {
                return false;
            }
        };
    }
}
