package games.moegirl.sinocraft.sinodivination.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

public class PktS2CTripodBuilt extends BaseClientPacket<PktS2CTripodBuilt> {

    private final boolean build;

    public PktS2CTripodBuilt(boolean build) {
        super();
        this.build = build;
    }

    public PktS2CTripodBuilt(FriendlyByteBuf buf) {
        super(buf);
        build = buf.readBoolean();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(build);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void onClient(NetworkEvent.Context context, Player player, net.minecraft.client.Minecraft mc) {

    }
}
