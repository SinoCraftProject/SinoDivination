package games.moegirl.sinocraft.sinodivination.datagen;

import com.mojang.datafixers.util.Pair;
import games.moegirl.sinocraft.sinocore.api.data.loot.BlockLootTableBase;
import games.moegirl.sinocraft.sinocore.api.data.loot.LootTableProviderBase;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SDLootTableProvider extends LootTableProviderBase {

    Set<Block> skippedBlocks = new HashSet<>();

    public SDLootTableProvider(DataGenerator pGenerator) {
        super(pGenerator, SinoDivination.MOD_ID);
    }

    @NotNull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> list = new ArrayList<>();
        skippedBlocks.addAll(SDTrees.COTINUS.register().addLoots(list::add).knownBlocks());
        skippedBlocks.addAll(SDTrees.SOPHORA.register().addLoots(list::add).knownBlocks());
        skippedBlocks.addAll(SDTrees.JUJUBE.register().addLoots(list::add).knownBlocks());
        skippedBlocks.addAll(SDWoodwork.COTINUS.register().addLoots(list::add).knownBlocks());
        skippedBlocks.addAll(SDWoodwork.SOPHORA.register().addLoots(list::add).knownBlocks());
        skippedBlocks.addAll(SDWoodwork.JUJUBE.register().addLoots(list::add).knownBlocks());
        list.addAll(super.getTables());
        return list;
    }

    @Override
    public BlockLoot getBlockLootTable() {
        return new BlockLootTableBase(SinoDivination.MOD_ID) {
            @Override
            protected void addTables() {
                skip(skippedBlocks);
                super.addTables();
            }
        };
    }
}
