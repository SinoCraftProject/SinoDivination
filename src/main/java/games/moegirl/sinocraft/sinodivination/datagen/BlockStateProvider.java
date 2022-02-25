package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinocore.api.data.gen.DefaultBlockStateProvider;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.Pot;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Set;
import java.util.stream.Collectors;

public class BlockStateProvider extends DefaultBlockStateProvider {
    public BlockStateProvider(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
        super(generator, modId, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // register models and state for blocks
        Set<Block> blocks = Registry.BLOCK.stream()
                .filter(b -> SinoDivination.MOD_ID.equals(Registry.BLOCK.getKey(b).getNamespace()))//filter block in mod
                .filter(b -> !(b instanceof Pot))//filter pot
                .collect(Collectors.toSet());

        registerBlock(blocks);
    }

    protected void registerBlock(Set<Block> blocks) {
        blocks.forEach(this::simpleBlock);
    }
}
