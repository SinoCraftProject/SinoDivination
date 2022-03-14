package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.api.tree.Tree;
import games.moegirl.sinocraft.sinodivination.blockentity.CotinusDoorBEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;

public class CotinusDoorBlock extends DoorBlock implements EntityBlock {

    public CotinusDoorBlock(Tree tree) {
        super(Properties.of(Material.WOOD, tree.getBlocks().planks().defaultMaterialColor())
                .strength(3.0F)
                .sound(SoundType.WOOD)
                .noOcclusion());
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        CotinusDoorBEntity blockEntity = (CotinusDoorBEntity) pLevel.getBlockEntity(pPos);
        assert blockEntity != null;
        blockEntity.get().setOwner(pPlacer);
        CotinusDoorBEntity blockEntity2 = (CotinusDoorBEntity) pLevel.getBlockEntity(pPos.above());
        assert blockEntity2 != null;
        blockEntity2.above();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CotinusDoorBEntity(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> GameEventListener getListener(Level pLevel, T pBlockEntity) {
        return (CotinusDoorBEntity) pBlockEntity;
    }
}
