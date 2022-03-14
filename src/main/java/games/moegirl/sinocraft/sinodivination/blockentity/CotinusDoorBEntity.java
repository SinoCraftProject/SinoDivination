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

public class CotinusDoorBEntity extends BlockEntity implements GameEventListener {
    private boolean isAbove = false;
    private boolean isOpen = false;
    private final OwnerChecker checker = new OwnerChecker() {
        @Override
        protected void setChanged() {
            CotinusDoorBEntity.this.setChanged();
        }
    };

    public CotinusDoorBEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(SDBlockEntities.COTINUS_DOOR.get(), pWorldPosition, pBlockState);
    }

    public void above() {
        isAbove = true;
        setChanged();
    }

    @SuppressWarnings("ConstantConditions")
    public boolean isOpen() {
        if (isAbove) {
            return ((CotinusDoorBEntity) level.getBlockEntity(getBlockPos().below())).isOpen;
        } else {
            return isOpen;
        }
    }

    private void setOpen(boolean open) {
        if (isAbove) {
            ((CotinusDoorBEntity) level.getBlockEntity(getBlockPos().below())).isOpen = open;
            setChanged();
        } else {
            isOpen = open;
            setChanged();
        }
    }

    @SuppressWarnings("ConstantConditions")
    public OwnerChecker get() {
        if (isAbove) {
            return ((CotinusDoorBEntity) level.getBlockEntity(getBlockPos().below())).checker;
        } else {
            return checker;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (isAbove) {
            tag.putBoolean(SinoDivination.MOD_ID + ".above", true);
        } else {
            tag.putBoolean(SinoDivination.MOD_ID + ".doorOpen", isOpen);
            checker.save(tag);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.getBoolean(SinoDivination.MOD_ID + ".above")) {
            isAbove = true;
        } else {
            isOpen = tag.getBoolean(SinoDivination.MOD_ID + ".doorOpen");
            checker.load(tag);
        }
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
                if (!get().isAllowed(pEntity)) {
                    pLevel.setBlock(pPos, getBlockState().setValue(DoorBlock.OPEN, false), 2);
                    clearRemoved();
                    pLevel.setBlockEntity(this);
                    return true;
                } else {
                    setOpen(true);
                }
            } else if (pEvent == GameEvent.BLOCK_CLOSE && isOpen()) {
                if (!get().isAllowed(pEntity)) {
                    pLevel.setBlock(pPos, getBlockState().setValue(DoorBlock.OPEN, true), 2);
                    clearRemoved();
                    pLevel.setBlockEntity(this);
                    return true;
                } else {
                    setOpen(false);
                }
            }
        }
        return false;
    }
}
