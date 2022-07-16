package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.api.woodwork.IWoodwork;
import games.moegirl.sinocraft.sinocore.api.woodwork.Woodwork;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;

public class JujubeFenceGate extends FenceGateBlock implements IWoodwork {

    private final Woodwork woodwork;

    public JujubeFenceGate(Properties properties, Woodwork woodwork) {
        super(properties);
        this.woodwork = woodwork;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.getValue(OPEN) ? 1 : 0;
    }

    @Override
    public Woodwork getWoodwork() {
        return woodwork;
    }
}
