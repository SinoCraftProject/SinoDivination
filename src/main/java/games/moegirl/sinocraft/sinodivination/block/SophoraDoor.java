package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinodivination.block.base.SophoraBlock;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.blockentity.SophoraDoorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SophoraDoor extends DoorBlock implements SophoraBlock {

    public SophoraDoor(Properties properties) {
        super(properties);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        SophoraDoorEntity blockEntity = (SophoraDoorEntity) pLevel.getBlockEntity(pPos);
        assert blockEntity != null;
        blockEntity.setEntity(pPlacer);
        SophoraDoorEntity blockEntity2 = (SophoraDoorEntity) pLevel.getBlockEntity(pPos.above());
        assert blockEntity2 != null;
        blockEntity2.above();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SDBlockEntities.SOPHORA_DOOR.get().create(pPos, pState);
    }
}
