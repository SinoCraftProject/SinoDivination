package games.moegirl.sinocraft.sinodivination.util.texture;

import javax.annotation.Nullable;

public record ProgressEntry(String name, int x, int y, @Nullable String begin, String finished, boolean isVertical, boolean isOpposite) {
}
