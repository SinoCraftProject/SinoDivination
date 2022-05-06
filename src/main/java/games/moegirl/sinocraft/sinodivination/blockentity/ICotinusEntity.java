package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.util.OwnerChecker;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;

public interface ICotinusEntity extends GameEventListener {

    BlockEntity getSelf();

    OwnerChecker owner();

    @Override
    default PositionSource getListenerSource() {
        return new BlockPositionSource(getSelf().getBlockPos());
    }

    @Override
    default int getListenerRadius() {
        return 1;
    }
}
