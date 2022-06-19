package games.moegirl.sinocraft.sinodivination.plugin.top;

import mcjty.theoneprobe.api.ITheOneProbe;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Function;

public class TOPPlugins implements Function<ITheOneProbe, Void> {

    @Override
    public Void apply(ITheOneProbe api) {
        api.registerBlockDisplayOverride(CotinusBlockDisplayOverride.INSTANCE);
        api.registerBlockDisplayOverride(JujubeBlockDisplayOverride.INSTANCE);
        api.registerBlockDisplayOverride(SophoraBlockDisplayOverride.INSTANCE);
        return null;
    }

    public static Component getPlayerName(@Nullable UUID uuid, Level level) {
        if (uuid == null) {
            return new TextComponent("null");
        } else {
            Player ownerPlayer = level.getPlayerByUUID(uuid);
            if (ownerPlayer != null) {
                return ownerPlayer.getName();
            } else {
                return new TextComponent("ID: " + uuid.toString().substring(0, 6));
            }
        }
    }
}
