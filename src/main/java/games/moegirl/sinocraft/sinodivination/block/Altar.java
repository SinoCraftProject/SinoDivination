package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinodivination.block.base.AltarBlock;
import games.moegirl.sinocraft.sinodivination.blockentity.AltarEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;

public class Altar extends AltarBlock<AltarEntity> {

    public Altar() {
        super(SDBlockEntities.ALTAR);
    }
}
