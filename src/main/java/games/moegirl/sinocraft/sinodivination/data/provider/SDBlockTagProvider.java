package games.moegirl.sinocraft.sinodivination.data.provider;

import games.moegirl.sinocraft.sinocore.api.data.BlockTagsProviderBase;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.block.base.WoodenChest;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import games.moegirl.sinocraft.sinodivination.data.SDTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

public class SDBlockTagProvider extends BlockTagsProviderBase {

    public SDBlockTagProvider(GatherDataEvent event) {
        super(event.getGenerator(), SinoDivination.MODID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags() {
        SDTrees.COTINUS.register().addBlockTags(this::tag);
        SDTrees.JUJUBE.register().addBlockTags(this::tag);
        SDTrees.SOPHORA.register().addBlockTags(this::tag);
        SDWoodwork.COTINUS.register().addBlockTags(this::tag);
        SDWoodwork.JUJUBE.register().addBlockTags(this::tag);
        SDWoodwork.SOPHORA.register().addBlockTags(this::tag);
        chest(SDBlocks.COTINUS_CHEST);
        chest(SDBlocks.JUJUBE_CHEST);
        chest(SDBlocks.SOPHORA_CHEST);
        add(SDTags.COTINUS_BLOCK, SDTrees.COTINUS.allBlocks());
        add(SDTags.COTINUS_BLOCK, SDWoodwork.COTINUS.allBlocks());
        add(SDTags.COTINUS_BLOCK, SDBlocks.COTINUS_CHEST.get());
        add(SDTags.SOPHORA_BLOCK, SDTrees.SOPHORA.allBlocks());
        add(SDTags.SOPHORA_BLOCK, SDWoodwork.SOPHORA.allBlocks());
        add(SDTags.SOPHORA_BLOCK, SDBlocks.SOPHORA_CHEST.get());
        tag(SDTags.HEAT_SOURCE).addTag(BlockTags.FIRE).addTag(BlockTags.CAMPFIRES);
        add(SDTags.HEAT_SOURCE, Blocks.LAVA, Blocks.TORCH, Blocks.REDSTONE_TORCH, Blocks.SOUL_TORCH);
        tag(SDTags.FIRE_SOURCE).addTag(BlockTags.FIRE).addTag(BlockTags.CAMPFIRES);
        tag(SDTags.SPAWN_DRAGONLIVER_MELON).addTag(Tags.Blocks.GRAVEL).addTag(BlockTags.DIRT);
        addPickaxe(SDBlocks.ORE_JADE.get(), SDBlocks.ORE_NITER.get(), SDBlocks.ORE_SULPHUR.get());
        addIronTool(SDBlocks.ORE_JADE.get());
        addStoneTool(SDBlocks.ORE_NITER.get(), SDBlocks.ORE_SULPHUR.get());

        tag(SDTags.FORGE_ORES_SULFUR).add(SDBlocks.ORE_SULPHUR.get());
        tag(SDTags.FORGE_ORES_NITER).add(SDBlocks.ORE_NITER.get());
    }

    protected <T extends WoodenChest> void chest(RegistryObject<T> chestObj) {
        T chest = chestObj.get();
        tag(Tags.Blocks.CHESTS_WOODEN).add(chest);
        tag(BlockTags.GUARDED_BY_PIGLINS).add(chest);
        tag(BlockTags.MINEABLE_WITH_AXE).add(chest);
        tag(BlockTags.FEATURES_CANNOT_REPLACE).add(chest);
        if (chest.isTrapped) {
            tag(Tags.Blocks.CHESTS_TRAPPED).add(chest);
        }
    }
}
