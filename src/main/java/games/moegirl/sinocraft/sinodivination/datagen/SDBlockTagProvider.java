package games.moegirl.sinocraft.sinodivination.datagen;

import games.moegirl.sinocraft.sinocore.api.data.BlockTagsProviderBase;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlockTags;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class SDBlockTagProvider extends BlockTagsProviderBase {

    public SDBlockTagProvider(GatherDataEvent event) {
        super(event.getGenerator(), SinoDivination.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags() {
        SDTrees.REGISTER.addBlockTags(this::tag);
        tag(SDBlockTags.COTINUS_WOODEN).add(SDTrees.COTINUS.getBlocks().allBlocks().toArray(Block[]::new));
        super.addTags();
    }

    @Override
    public void addPickaxe() {

    }

    @Override
    public void addAxe() {

    }

    @Override
    public void addShovel() {

    }

    @Override
    public void addHoe() {

    }

    @Override
    public void addStoneTool() {

    }

    @Override
    public void addIronTool() {

    }

    @Override
    public void addDiamondTool() {

    }
}
