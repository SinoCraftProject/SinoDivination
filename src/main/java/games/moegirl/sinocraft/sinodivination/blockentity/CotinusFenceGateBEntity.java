package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.util.OwnerChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;

import javax.annotation.Nullable;

public class CotinusFenceGateBEntity extends BlockEntity implements GameEventListener {

    private final OwnerChecker checker = new OwnerChecker() {
        @Override
        protected void setChanged() {
            CotinusFenceGateBEntity.this.setChanged();
        }
    };
    private boolean isOpen = false;

    public CotinusFenceGateBEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(SDBlockEntities.COTINUS_FENCE_GATE.get(), pWorldPosition, pBlockState);
    }

    public OwnerChecker getChecker() {
        return checker;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean(SinoDivination.MOD_ID + ".doorOpen", isOpen);
        checker.save(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        isOpen = pTag.getBoolean(SinoDivination.MOD_ID + ".doorOpen");
        checker.load(pTag);
    }

    @Override
    public PositionSource getListenerSource() {
        return new BlockPositionSource(worldPosition);
    }

    @Override
    public int getListenerRadius() {
        return 1;
    }

    @Override
    public boolean handleGameEvent(Level pLevel, GameEvent pEvent, @Nullable Entity pEntity, BlockPos pPos) {
        if (!pLevel.isClientSide && pPos.equals(getBlockPos())) {
            if (pEvent == GameEvent.BLOCK_OPEN && !isOpen()) {
                if (!checker.isAllowed(pEntity)) {
                    pLevel.setBlock(pPos, getBlockState().setValue(DoorBlock.OPEN, false), 2);
                    clearRemoved();
                    pLevel.setBlockEntity(this);
                    return true;
                } else {
                    isOpen = true;
                }
            } else if (pEvent == GameEvent.BLOCK_CLOSE && isOpen()) {
                if (!checker.isAllowed(pEntity)) {
                    pLevel.setBlock(pPos, getBlockState().setValue(DoorBlock.OPEN, true), 2);
                    clearRemoved();
                    pLevel.setBlockEntity(this);
                    return true;
                } else {
                    isOpen = false;
                }
            }
        }
        return false;
    }
}
