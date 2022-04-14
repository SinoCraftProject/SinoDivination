package games.moegirl.sinocraft.sinodivination.world;

import games.moegirl.sinocraft.sinocore.api.world.OreFeatureBuilder;
import games.moegirl.sinocraft.sinocore.api.world.PlacedFeatureRegister;
import games.moegirl.sinocraft.sinocore.api.world.TreeFeatureBuilder;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraftforge.common.util.Lazy;

public class SDPlacedFeatures {

    private static final PlacedFeatureRegister REGISTER = new PlacedFeatureRegister(SinoDivination.MOD_ID);

    private static final PlacedFeatureRegister.Entry<OreConfiguration, OreFeatureBuilder> JADE =
            REGISTER.registerOre(SDBlocks.ORE_JADE.getId().getPath(), builder -> builder
                    .addStoneOre(SDBlocks.ORE_JADE)
                    .fromConfiguration(OreFeatures.ORE_GOLD_BURIED.value().config())
                    .fromModifier(OrePlacements.ORE_GOLD.value())
                    .replaceModifier(HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(10))));

    private static final PlacedFeatureRegister.Entry<OreConfiguration, OreFeatureBuilder> SULFUR =
            REGISTER.registerOre(SDBlocks.ORE_SULPHUR.getId().getPath(), builder -> builder
                    .addStoneOre(SDBlocks.ORE_SULPHUR)
                    .fromConfiguration(OreFeatures.ORE_IRON.value().config())
                    .fromModifier(OrePlacements.ORE_IRON_MIDDLE.value())
                    .replaceModifier(HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(8))));

    private static final PlacedFeatureRegister.Entry<OreConfiguration, OreFeatureBuilder> NITER =
            REGISTER.registerOre(SDBlocks.ORE_NITER.getId().getPath(), builder -> builder
                    .addStoneOre(SDBlocks.ORE_NITER)
                    .fromConfiguration(OreFeatures.ORE_IRON.value().config())
                    .fromModifier(OrePlacements.ORE_IRON_MIDDLE.value())
                    .replaceModifier(HeightRangePlacement.triangle(VerticalAnchor.absolute(8), VerticalAnchor.absolute(64))));

    public static final Lazy<TreeFeatureBuilder> COTINUS = Lazy.of(() -> new TreeFeatureBuilder()
            .fromConfiguration(TreeFeatures.OAK.value().config())
            .fromModifier(TreePlacements.OAK_CHECKED.value())
            .minimumSize(new TwoLayersFeatureSize(1, 0, 1))
            .foliage(BlockStateProvider.simple(SDTrees.COTINUS.getBlocks().leaves()))
            .foliagePlacer(new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3))
            .trunk(BlockStateProvider.simple(SDTrees.COTINUS.getBlocks().log()))
            .trunkPlacer(new StraightTrunkPlacer(4, 2, 0)));

    public static void register() {
        REGISTER.register();
    }
}
