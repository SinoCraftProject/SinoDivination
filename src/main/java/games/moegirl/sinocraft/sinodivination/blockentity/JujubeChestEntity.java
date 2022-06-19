package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class JujubeChestEntity extends WoodenChestEntity {

    public JujubeChestEntity(BlockPos blockPos, BlockState state) {
        super(SDBlocks.JUJUBE_CHEST.get(), blockPos, state);
    }
}
