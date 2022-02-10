package com.example.examplemod.item;

import com.example.examplemod.SinoDivination;
import com.example.examplemod.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, SinoDivination.MOD_ID);

    public static final RegistryObject<Item> ORE_JADE = REGISTRY.register("ore_jade", () -> new BlockItem(ModBlocks.ORE_JADE.get(), defaultBuilder()));
    public static final RegistryObject<Item> JADE = REGISTRY.register("jade", () -> new Item(defaultBuilder()));

    public static CreativeModeTab creativeTab = new CreativeModeTab(SinoDivination.MOD_ID) {
        @NonNull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ORE_JADE.get());
        }
    };

    public static Item.Properties defaultBuilder() {
        return new Item.Properties().tab(creativeTab);
    }
}
