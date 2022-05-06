package games.moegirl.sinocraft.sinodivination.network;

import games.moegirl.sinocraft.sinocore.api.utility.Id;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.concurrent.atomic.AtomicInteger;

public class SDNetworks {

    public static final Id ID = new Id();

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SinoDivination.MOD_ID, "network"), () -> "1", s -> true, s -> true);

    public static void register() {

    }
}
