package games.moegirl.sinocraft.sinodivination.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.material.Material;

public class OreBlock extends net.minecraft.world.level.block.OreBlock {

    public OreBlock(Material material) {
        super(Properties.of(material)
                .requiresCorrectToolForDrops()
                .explosionResistance(3.0F));
    }

    public OreBlock(Material material, int minExp, int maxExp) {
        super(Properties.of(material)
                .requiresCorrectToolForDrops()
                .explosionResistance(3.0F), UniformInt.of(minExp, maxExp));
    }
}
