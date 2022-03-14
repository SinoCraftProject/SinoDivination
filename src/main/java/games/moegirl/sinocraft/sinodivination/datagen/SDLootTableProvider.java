package games.moegirl.sinocraft.sinodivination.datagen;

import com.mojang.datafixers.util.Pair;
import games.moegirl.sinocraft.sinocore.api.data.gen.loot.LootTableProviderBase;
import games.moegirl.sinocraft.sinocore.api.data.gen.loot.block.BlockLootTableBase;
import games.moegirl.sinocraft.sinocore.api.tree.TreeBlockLoot;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SDLootTableProvider extends LootTableProviderBase {

    Set<TreeBlockLoot> skippedBlocks = Collections.emptySet();

    public SDLootTableProvider(DataGenerator pGenerator) {
        super(pGenerator, SinoDivination.MOD_ID);
    }

    @NotNull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> list = new ArrayList<>();
        skippedBlocks = SDTrees.REGISTER.addLoots(list::add);
        list.addAll(super.getTables());
        return list;
    }

    @Override
    public BlockLoot getBlockLootTable() {
        return new BlockLootTableBase(SinoDivination.MOD_ID) {
            @Override
            protected void addTables() {
                skippedBlocks.stream()
                        .map(TreeBlockLoot::knownBlocks)
                        .forEach(this::skip);
                super.addTables();
            }
        };
    }
}
