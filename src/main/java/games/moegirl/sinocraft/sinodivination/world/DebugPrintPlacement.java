package games.moegirl.sinocraft.sinodivination.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.moegirl.sinocraft.sinodivination.handler.RegisterEventHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.Random;
import java.util.stream.Stream;

public class DebugPrintPlacement extends PlacementModifier {
    public static final Codec<DebugPrintPlacement> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(Codec.STRING.fieldOf("name").forGetter(s -> s.name))
            .apply(instance, DebugPrintPlacement::new));

    private final String name;

    public DebugPrintPlacement(String name) {
        this.name = name;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext arg, Random random, BlockPos arg2) {
        System.out.println(name + ": " + arg2 + " - " + arg.getBlockState(arg2));
        return Stream.of(arg2);
    }

    @Override
    public PlacementModifierType<?> type() {
        return RegisterEventHandler.P;
    }
}
