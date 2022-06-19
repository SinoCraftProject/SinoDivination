package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.util.container.ComposeItemHandler;
import games.moegirl.sinocraft.sinodivination.util.container.InputOnlyContainer;
import games.moegirl.sinocraft.sinodivination.util.container.OutputOnlyContainer;
import games.moegirl.sinocraft.sinodivination.world.SDPatterns;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TripodEntity extends BlockEntity implements IAltarEntity, BlockEntityTicker<TripodEntity>, GameEventListener {

    private final InputOnlyContainer in = new InputOnlyContainer(1, 1).bindEntityChange(this);
    private final OutputOnlyContainer out = new OutputOnlyContainer(1).bindEntityChange(this);
    private final ComposeItemHandler compose = new ComposeItemHandler().append(in).append(out);
    private PositionSource position;

    @Nullable
    private AltarStructure altar = null;
    private boolean isResume = false;
    private int runningTick = 0;

    public TripodEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
        this.position = new BlockPositionSource(pWorldPosition);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> compose).cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public ItemStack takeItem() {
        if (out.getStackInSlot(0).isEmpty()) {
            return takeItem(in);
        } else {
            return takeItem(out);
        }
    }

    private ItemStack takeItem(IItemHandlerModifiable handler) {
        ItemStack stack = handler.getStackInSlot(0);
        handler.setStackInSlot(0, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public ItemStack putItem(ItemStack stack) {
        return in.insertItem2(0, stack, false);
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, TripodEntity pBlockEntity) {
        if (isResume) {
            updateAltar(pLevel, pPos);
            isResume = false;
        }

        if (altar == null) {
            runningTick = 0;
        }

        if (runningTick > 0) {
            runningTick--;
            setChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        position = new BlockPositionSource(worldPosition);
    }

    @Override
    public PositionSource getListenerSource() {
        return position;
    }

    @Override
    public int getListenerRadius() {
        return 10;
    }

    @Override
    public boolean handleGameEvent(Level pLevel, GameEvent pEvent, @Nullable Entity pEntity, BlockPos pPos) {
        return updateAltar(pLevel, pPos);
    }

    public void clearProgress() {
        runningTick = 0;
        if (level != null) {
            level.setBlockAndUpdate(worldPosition, getBlockState());
        }
    }

    private boolean updateAltar(Level pLevel, BlockPos pPos) {
        BlockPattern.BlockPatternMatch match = SDPatterns.ALTAR.find(pLevel, pPos);
        if (match == null && altar == null) return false;
        boolean isChanged = false;
        if (match == null) {
            altar = null;
            isChanged = true;
        } else {
            BlockEntity east = match.getBlock(4, 0, 0).getEntity();
            BlockEntity west = match.getBlock(-4, 0, 0).getEntity();
            BlockEntity north = match.getBlock(0, 0, -4).getEntity();
            BlockEntity south = match.getBlock(0, 0, 4).getEntity();
            if (east instanceof AltarEntity ae && west instanceof AltarEntity aw && north instanceof AltarEntity an && south instanceof AltarEntity as) {
                if (altar == null) {
                    altar = new AltarStructure(ae, aw, an, as);
                    isChanged = true;
                } else {
                    if (altar.east != east || altar.west != west || altar.north != north || altar.south != south) {
                        altar = new AltarStructure(ae, aw, an, as);
                        isChanged = true;
                    }
                }
            } else if (altar != null) {
                altar = null;
                isChanged = true;
            }
        }
        if (isChanged && altar != null) {
            updateAltarEntity(pLevel, altar.east, Direction.EAST);
            updateAltarEntity(pLevel, altar.west, Direction.WEST);
            updateAltarEntity(pLevel, altar.north, Direction.NORTH);
            updateAltarEntity(pLevel, altar.south, Direction.SOUTH);
            pLevel.addParticle(ParticleTypes.SMOKE, pPos.getX(), pPos.getY() + 0.5, pPos.getZ(), 0, 0, 0);
            setChanged();
        }
        return isChanged;
    }

    private void updateAltarEntity(Level level, AltarEntity entity, Direction direction) {
        entity.setDirection(direction);
        BlockPos pos = entity.getBlockPos();
        level.addParticle(ParticleTypes.SMOKE, pos.getX(), pos.getY() + 0.5, pos.getZ(), 0, 0, 0);
    }

    record AltarStructure(AltarEntity east, AltarEntity west, AltarEntity north, AltarEntity south) { }
}
