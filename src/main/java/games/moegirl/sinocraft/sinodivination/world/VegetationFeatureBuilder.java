package games.moegirl.sinocraft.sinodivination.world;

import games.moegirl.sinocraft.sinocore.api.world.BaseFeatureBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * A feature to create vegetation, like flower
 */
public class VegetationFeatureBuilder extends BaseFeatureBuilder<RandomPatchConfiguration, VegetationFeatureBuilder> {

    private int tries = 96;
    private int xz = 7, y = 3;
    private Holder<PlacedFeature> supplier;

    public VegetationFeatureBuilder() {
        super(Feature.RANDOM_PATCH);
    }

    /**
     * try times
     *
     * @param tries tries
     * @return this builder
     */
    public VegetationFeatureBuilder tries(int tries) {
        this.tries = tries;
        return this;
    }

    /**
     * spread x, y
     *
     * @param xzSpread x, y
     * @return this builder
     */
    public VegetationFeatureBuilder spreadXZ(int xzSpread) {
        this.xz = xzSpread;
        return this;
    }

    /**
     * spread y
     *
     * @param ySpread y
     * @return this builder
     */
    public VegetationFeatureBuilder spreadY(int ySpread) {
        this.y = ySpread;
        return this;
    }

    /**
     * A feature to generate vegetation
     *
     * @param feature feature
     * @return this builder
     */
    public VegetationFeatureBuilder feature(Holder<PlacedFeature> feature) {
        this.supplier = feature;
        return this;
    }

    @Override
    public VegetationFeatureBuilder fromConfiguration(RandomPatchConfiguration parent) {
        tries = parent.tries();
        xz = parent.xzSpread();
        y = parent.ySpread();
        supplier = parent.feature();
        return this;
    }

    @Override
    protected RandomPatchConfiguration buildConfiguration() {
        return new RandomPatchConfiguration(tries, xz, y, supplier);
    }
}
