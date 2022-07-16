package games.moegirl.sinocraft.sinodivination.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

public abstract class BaseServerPacket<T extends BaseServerPacket<T>> extends BasePacket<T> {

    public BaseServerPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    public BaseServerPacket() {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void onClient(NetworkEvent.Context context) {
        throw new IllegalStateException("Packet " + getClass().getSimpleName() + " can only execute at server side.");
    }

    @Override
    protected void onServer(NetworkEvent.Context context) {
        onServer(context, context.getSender());
    }

    protected abstract void onServer(NetworkEvent.Context context, ServerPlayer sender);

    public void sendTo() {
        SDNetworks.CHANNEL.sendToServer(this);
    }
}
