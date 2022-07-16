package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.api.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.api.utility.BlockLootables;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootTable;

public class OreSulphur extends OreBlock implements ILootableBlock {

    public OreSulphur() {
        super(Properties.of(Material.STONE)
                .requiresCorrectToolForDrops()
                .explosionResistance(3.0F));
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        return helper.createOreDrops(this, SDItems.SULPHUR.get(), 1, 2);
    }
}
