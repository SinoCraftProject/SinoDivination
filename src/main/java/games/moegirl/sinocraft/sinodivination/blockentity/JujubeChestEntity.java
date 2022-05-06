package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import org.jetbrains.annotations.Nullable;

public class JujubeChestEntity extends WoodenChestEntity implements GameEventListener {

    private boolean isOpen = false;

    public JujubeChestEntity(BlockPos blockPos, BlockState state) {
        super(SDBlocks.JUJUBE_CHEST.get(), blockPos, state);
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public PositionSource getListenerSource() {
        return new BlockPositionSource(getBlockPos());
    }

    @Override
    public int getListenerRadius() {
        return 1;
    }

    @Override
    public boolean handleGameEvent(Level pLevel, GameEvent pEvent, @Nullable Entity pEntity, BlockPos pPos) {
        if (pPos.equals(getBlockPos())) {
            if (GameEvent.CONTAINER_OPEN.equals(pEvent)) {
                isOpen = true;
            } else if (GameEvent.CONTAINER_CLOSE.equals(pEvent)) {
                isOpen = false;
            }
        }
        return false;
    }
}
