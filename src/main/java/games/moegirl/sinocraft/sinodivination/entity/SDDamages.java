package games.moegirl.sinocraft.sinodivination.entity;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.world.damagesource.DamageSource;

public class SDDamages {
    public static final String MSG_SOPHORA_DOOR = SinoDivination.MODID + ".damage.sophora_door";

    public static final String KEY_SOPHORA_DOOR = "death.attack." + MSG_SOPHORA_DOOR;

    public static DamageSource bySophoraDoor() {
        return new DamageSource(MSG_SOPHORA_DOOR);
    }
}
