package games.moegirl.sinocraft.sinodivination.blockentity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;

public interface ISophoraEntity extends GameEventListener {

    BlockEntity getSelf();

    void setEntity(Entity entity);

    @Override
    default PositionSource getListenerSource() {
        return new BlockPositionSource(getSelf().getBlockPos());
    }

    @Override
    default int getListenerRadius() {
        return 1;
    }
}
