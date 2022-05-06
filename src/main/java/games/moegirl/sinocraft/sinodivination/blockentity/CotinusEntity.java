package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.util.OwnerChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public class CotinusEntity extends BlockEntity implements ICotinusEntity {

    protected boolean isOpen = false;
    protected final OwnerChecker checker = OwnerChecker.forBlock(this);

    public CotinusEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
        setChanged();
    }

    @Override
    public BlockEntity getSelf() {
        return this;
    }

    @Override
    public OwnerChecker owner() {
        return checker;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        isOpen = pTag.getBoolean(SinoDivination.MOD_ID + ".doorOpen");
        checker.load(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean(SinoDivination.MOD_ID + ".doorOpen", isOpen);
        checker.save(pTag);
    }

    @Override
    public boolean handleGameEvent(Level pLevel, GameEvent pEvent, @Nullable Entity pEntity, BlockPos pPos) {
        if (!pLevel.isClientSide && pPos.equals(worldPosition)) {
            if (pEvent == GameEvent.BLOCK_OPEN && !isOpen()) {
                if (!owner().isAllowed(pEntity)) {
                    pLevel.setBlock(pPos, getBlockState().setValue(BlockStateProperties.OPEN, false), 2);
                    return true;
                } else {
                    setOpen(true);
                }
            } else if (pEvent == GameEvent.BLOCK_CLOSE && isOpen()) {
                if (!owner().isAllowed(pEntity)) {
                    pLevel.setBlock(pPos, getBlockState().setValue(BlockStateProperties.OPEN, true), 2);
                    return true;
                } else {
                    setOpen(false);
                }
            }
        }
        return false;
    }
}
