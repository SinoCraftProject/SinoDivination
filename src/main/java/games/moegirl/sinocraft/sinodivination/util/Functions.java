package games.moegirl.sinocraft.sinodivination.util;

import java.util.function.Supplier;

public class Functions {

    public static <T> Supplier<T> constructor(Class<? extends T> aClass) {
        return () -> {
            try {
                return aClass.getConstructor().newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException("Can't create " + aClass.getCanonicalName() + " by no-parameter public constructor.", e);
            }
        };
    }
}
