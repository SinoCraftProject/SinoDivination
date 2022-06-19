package games.moegirl.sinocraft.sinodivination.util.container;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public interface SlotChecker {

    boolean check(int slot, ItemStack stack);

    static SlotChecker not(SlotChecker another) {
        return (_1, _2) -> !another.check(_1, _2);
    }

    static SlotChecker and(SlotChecker a, SlotChecker b) {
        return (i, s) -> a.check(i, s) && b.check(i, s);
    }

    static SlotChecker or(SlotChecker a, SlotChecker b) {
        return (i, s) -> a.check(i, s) || b.check(i, s);
    }

    static SlotChecker of(Supplier<? extends ItemLike> item) {
        return (__, s) -> s.is(item.get().asItem());
    }

    static SlotChecker of(ItemLike item) {
        return (__, s) -> s.is(item.asItem());
    }

    static SlotChecker of(TagKey<Item> tag) {
        return (__, s) -> s.is(tag);
    }

    static SlotChecker of(int slot, Supplier<? extends ItemLike> item) {
        return (i, s) -> i != slot || s.is(item.get().asItem());
    }

    static SlotChecker of(int slot, ItemLike item) {
        return (i, s) -> i != slot || s.is(item.asItem());
    }

    static SlotChecker of(int slot, TagKey<Item> tag) {
        return (i, s) -> i != slot && s.is(tag);
    }

    static SlotChecker ban(int slot) {
        return (i, __) -> i != slot;
    }

    static SlotChecker ban(Supplier<? extends ItemLike> item) {
        return (__, s) -> !s.is(item.get().asItem());
    }

    static SlotChecker ban(ItemLike item) {
        return (__, s) -> !s.is(item.asItem());
    }

    static SlotChecker ban(TagKey<Item> tag) {
        return (__, s) -> !s.is(tag);
    }

    static SlotChecker ban(int slot, Supplier<? extends ItemLike> item) {
        return (i, s) -> i != slot || !s.is(item.get().asItem());
    }

    static SlotChecker ban(int slot, ItemLike item) {
        return (i, s) -> i != slot || !s.is(item.asItem());
    }

    static SlotChecker ban(int slot, TagKey<Item> tag) {
        return (i, s) -> i != slot && !s.is(tag);
    }

}
