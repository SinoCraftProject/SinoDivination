package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.api.woodwork.IWoodwork;
import games.moegirl.sinocraft.sinocore.api.woodwork.Woodwork;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;

public class JujubeTrapdoor extends TrapDoorBlock implements IWoodwork {

    private final Woodwork woodwork;

    public JujubeTrapdoor(Properties properties, Woodwork woodwork) {
        super(properties);
        this.woodwork = woodwork;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        return pState.getValue(OPEN) ? 1 : 0;
    }

    @Override
    public Woodwork getWoodwork() {
        return woodwork;
    }
}
