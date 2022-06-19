package games.moegirl.sinocraft.sinodivination.blockentity;

import net.minecraft.world.item.ItemStack;

public interface IAltarEntity {

    ItemStack takeItem();

    ItemStack putItem(ItemStack stack);
}
