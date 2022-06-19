package games.moegirl.sinocraft.sinodivination.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class BasePacket<T extends BasePacket<T>> {

    public BasePacket(FriendlyByteBuf buf) {

    }

    public BasePacket() {

    }

    public abstract void encode(FriendlyByteBuf buf);

    public void consume(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            context.enqueueWork(() -> onServer(context));
        } else {
            context.enqueueWork(() -> onClient(context));
        }
        context.setPacketHandled(true);
    }

    protected abstract void onClient(NetworkEvent.Context context);

    protected abstract void onServer(NetworkEvent.Context context);
}
