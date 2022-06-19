package games.moegirl.sinocraft.sinodivination.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public abstract class BaseClientPacket<T extends BaseClientPacket<T>> extends BasePacket<T> {

    public BaseClientPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    public BaseClientPacket() {
    }

    @Override
    protected void onClient(NetworkEvent.Context context) {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
        onClient(context, mc.player, mc);
    }

    @Override
    protected void onServer(NetworkEvent.Context context) {
        throw new IllegalStateException("Packet " + getClass().getSimpleName() + " can only execute at client side.");
    }

    @OnlyIn(Dist.CLIENT)
    protected abstract void onClient(NetworkEvent.Context context, Player player, net.minecraft.client.Minecraft mc);

    public boolean sendTo(Player player) {
        if (player instanceof ServerPlayer sp) {
            SDNetworks.CHANNEL.sendTo(this, sp.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            return true;
        }
        return false;
    }

    public void sendToOrThrow(Player player) {
        if (!sendTo(player)) {
            throw new IllegalArgumentException("Player " + player + " is not a server player");
        }
    }
}
