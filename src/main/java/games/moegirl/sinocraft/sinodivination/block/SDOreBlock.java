package games.moegirl.sinocraft.sinodivination.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.material.Material;

public class SDOreBlock extends OreBlock {

    public SDOreBlock(Material material) {
        super(Properties.of(material)
                .requiresCorrectToolForDrops()
                .explosionResistance(3.0F));
    }

    public SDOreBlock(Material material, int minExp, int maxExp) {
        super(Properties.of(material)
                .requiresCorrectToolForDrops()
                .explosionResistance(3.0F), UniformInt.of(minExp, maxExp));
    }
}
