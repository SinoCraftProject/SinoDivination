package games.moegirl.sinocraft.sinodivination.plugin.top;

import games.moegirl.sinocraft.sinodivination.blockentity.ICotinusEntity;
import games.moegirl.sinocraft.sinodivination.util.OwnerChecker;
import games.moegirl.sinocraft.sinodivination.util.SDLangKeys;
import mcjty.theoneprobe.api.IBlockDisplayOverride;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;
import java.util.UUID;

public enum CotinusBlockDisplayOverride implements IBlockDisplayOverride {

    INSTANCE;

    @Override
    public boolean overrideStandardInfo(ProbeMode probeMode, IProbeInfo info, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
        BlockEntity entity = level.getBlockEntity(iProbeHitData.getPos());
        if (entity instanceof ICotinusEntity cotinus) {
            OwnerChecker owner = cotinus.owner();
            info.text(SDLangKeys.TOP_BLOCK_OWNER, TOPPlugins.getPlayerName(owner.getOwner(), level));
            Set<UUID> allowed = owner.getAllowed();
            if (!allowed.isEmpty()) {
                info.text(SDLangKeys.TOP_BLOCK_ALLOWED, allowed.size());
                if (probeMode != ProbeMode.NORMAL) {
                    for (UUID uuid : allowed) {
                        info.text(TOPPlugins.getPlayerName(uuid, level));
                    }
                }
            }
            return true;
        }
        return false;
    }
}
