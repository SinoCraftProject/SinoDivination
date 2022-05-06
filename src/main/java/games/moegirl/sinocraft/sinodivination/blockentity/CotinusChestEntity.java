package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.block.WoodenChest;
import games.moegirl.sinocraft.sinodivination.util.OwnerChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class CotinusChestEntity extends WoodenChestEntity implements ICotinusEntity {

    private final OwnerChecker checker = OwnerChecker.forBlock(this);

    public CotinusChestEntity(BlockPos blockPos, BlockState state) {
        super(SDBlocks.COTINUS_CHEST.get(), blockPos, state);
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
        checker.load(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        checker.save(pTag);
    }

    @Override
    public boolean handleGameEvent(Level pLevel, GameEvent pEvent, @Nullable Entity pEntity, BlockPos pPos) {
        return false;
    }
}
