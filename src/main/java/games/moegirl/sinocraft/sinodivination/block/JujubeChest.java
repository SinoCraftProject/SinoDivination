package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinodivination.blockentity.JujubeChestEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import org.jetbrains.annotations.Nullable;

public class JujubeChest extends WoodenChest {

    public JujubeChest() {
        super(SDWoodwork.JUJUBE, SDBlockEntities.JUJUBE_CHEST, SDItems.JUJUBE_CHEST);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        return pLevel.getBlockEntity(pPos, blockEntityType.get())
                .map(be -> (JujubeChestEntity) be)
                .filter(JujubeChestEntity::isOpen)
                .isPresent() ? 1 : 0;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getListener(Level pLevel, T pBlockEntity) {
        return (GameEventListener) pBlockEntity;
    }
}
