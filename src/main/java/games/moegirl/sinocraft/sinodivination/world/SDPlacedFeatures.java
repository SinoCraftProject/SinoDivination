package games.moegirl.sinocraft.sinodivination.world;

import games.moegirl.sinocraft.sinocore.api.world.OreFeatureBuilder;
import games.moegirl.sinocraft.sinocore.api.world.PlacedFeatureRegister;
import games.moegirl.sinocraft.sinocore.api.world.PlacedFilter;
import games.moegirl.sinocraft.sinocore.api.world.TreeFeatureBuilder;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.util.SDTags;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class SDPlacedFeatures {

    public static final PlacedFeatureRegister REGISTRY = new PlacedFeatureRegister(SinoDivination.MOD_ID);

    private static final PlacedFeatureRegister.Entry<OreConfiguration, OreFeatureBuilder> JADE =
            REGISTRY.registerOre(SDBlocks.ORE_JADE.getId().getPath(), builder -> builder
                    .addStoneOre(SDBlocks.ORE_JADE)
                    .fromConfiguration(OreFeatures.ORE_GOLD_BURIED.value().config())
                    .fromModifier(OrePlacements.ORE_GOLD.value())
                    .replaceModifier(HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(10))));

    private static final PlacedFeatureRegister.Entry<OreConfiguration, OreFeatureBuilder> SULFUR =
            REGISTRY.registerOre(SDBlocks.ORE_SULPHUR.getId().getPath(), builder -> builder
                    .addStoneOre(SDBlocks.ORE_SULPHUR)
                    .fromConfiguration(OreFeatures.ORE_IRON.value().config())
                    .fromModifier(OrePlacements.ORE_IRON_MIDDLE.value())
                    .replaceModifier(HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(8))));

    private static final PlacedFeatureRegister.Entry<OreConfiguration, OreFeatureBuilder> NITER =
            REGISTRY.registerOre(SDBlocks.ORE_NITER.getId().getPath(), builder -> builder
                    .addStoneOre(SDBlocks.ORE_NITER)
                    .fromConfiguration(OreFeatures.ORE_IRON.value().config())
                    .fromModifier(OrePlacements.ORE_IRON_MIDDLE.value())
                    .replaceModifier(HeightRangePlacement.triangle(VerticalAnchor.absolute(8), VerticalAnchor.absolute(64))));

    public static final PlacedFeatureRegister.Entry<RandomPatchConfiguration, SpreadFeatureBuilder> RICE =
            REGISTRY.register(GenerationStep.Decoration.VEGETAL_DECORATION, SDBlocks.RICE.getId().getPath(),
                    PlacedFilter.ofBiomes(Biomes.RIVER, Biomes.PLAINS),
                    () -> new SpreadFeatureBuilder()
                            .tries(30)
                            .spreadY(2)
                            .spreadXZ(5)
                            .feature(new PlacedFeature(Holder.direct(simple(SDBlocks.RICE).buildConfigured()), List.of()))
                            .addModifier(EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.ONLY_IN_AIR_PREDICATE, 1))
                            .addModifier(BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluid(Fluids.WATER, Vec3i.ZERO)))
                            .addModifier(new DebugPrintPlacement("Rice")));

    public static final PlacedFeatureRegister.Entry<RandomPatchConfiguration, SpreadFeatureBuilder> REHMANNIA =
            REGISTRY.register(GenerationStep.Decoration.VEGETAL_DECORATION, SDBlocks.RICE.getId().getPath(),
                    PlacedFilter.ofBiomes(Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE, Biomes.RIVER),
                    () -> new SpreadFeatureBuilder()
                            .tries(30)
                            .spreadY(2)
                            .spreadXZ(5)
                            .feature(new PlacedFeature(Holder.direct(simple(SDBlocks.REHMANNIA).buildConfigured()), List.of()))
                            .addModifier(EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.ONLY_IN_AIR_PREDICATE, 1))
                            .addModifier(BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockTags.DIRT)))
                            .addModifier(new DebugPrintPlacement("Rehmannia")));

    public static final PlacedFeatureRegister.Entry<RandomPatchConfiguration, SpreadFeatureBuilder> DRAGONLIVER_MELON =
            REGISTRY.register(GenerationStep.Decoration.VEGETAL_DECORATION, SDBlocks.RICE.getId().getPath(),
                    PlacedFilter.ofBiomes(Biomes.SNOWY_BEACH, Biomes.SNOWY_PLAINS, Biomes.SNOWY_SLOPES, Biomes.SNOWY_TAIGA),
                    () -> new SpreadFeatureBuilder()
                            .tries(30)
                            .spreadY(2)
                            .spreadXZ(5)
                            .feature(new PlacedFeature(Holder.direct(simple(SDBlocks.DRAGONLIVER_MELON).buildConfigured()), List.of()))
                            .addModifier(EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.ONLY_IN_AIR_PREDICATE, 1))
                            .addModifier(BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(SDTags.SPAWN_DRAGONLIVER_MELON)))
                            .addModifier(new DebugPrintPlacement("DragonliverMelon")));

    public static final Lazy<TreeFeatureBuilder> COTINUS = Lazy.of(() -> new TreeFeatureBuilder()
            .fromConfiguration(TreeFeatures.OAK.value().config())
            .fromModifier(TreePlacements.OAK_CHECKED.value())
            .minimumSize(new TwoLayersFeatureSize(1, 0, 1))
            .foliage(BlockStateProvider.simple(SDTrees.COTINUS.leaves()))
            .foliagePlacer(new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3))
            .trunk(BlockStateProvider.simple(SDTrees.COTINUS.log()))
            .trunkPlacer(new StraightTrunkPlacer(4, 2, 0)));

    public static final Lazy<TreeFeatureBuilder> JUJUBE = Lazy.of(() -> new TreeFeatureBuilder()
            .fromConfiguration(TreeFeatures.OAK.value().config())
            .fromModifier(TreePlacements.OAK_CHECKED.value())
            .minimumSize(new TwoLayersFeatureSize(1, 0, 1))
            .foliage(BlockStateProvider.simple(SDTrees.JUJUBE.leaves()))
            .foliagePlacer(new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3))
            .trunk(BlockStateProvider.simple(SDTrees.JUJUBE.log()))
            .trunkPlacer(new StraightTrunkPlacer(4, 2, 0)));

    public static final Lazy<TreeFeatureBuilder> SOPHORA = Lazy.of(() -> new TreeFeatureBuilder()
            .fromConfiguration(TreeFeatures.OAK.value().config())
            .fromModifier(TreePlacements.OAK_CHECKED.value())
            .minimumSize(new TwoLayersFeatureSize(1, 0, 1))
            .foliage(BlockStateProvider.simple(SDTrees.SOPHORA.leaves()))
            .foliagePlacer(new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3))
            .trunk(BlockStateProvider.simple(SDTrees.SOPHORA.log()))
            .trunkPlacer(new StraightTrunkPlacer(4, 2, 0)));

    private static SimpleFeatureBuilder simple(RegistryObject<? extends Block> block) {
        return new SimpleFeatureBuilder().block(block.get());
    }
}
