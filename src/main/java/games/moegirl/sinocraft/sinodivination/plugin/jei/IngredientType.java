package games.moegirl.sinocraft.sinodivination.plugin.jei;

import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.registration.IModIngredientRegistration;

import java.util.Collection;
import java.util.function.Supplier;

public interface IngredientType<T> extends IIngredientType<T>, IIngredientHelper<T>, IIngredientRenderer<T>, Supplier<Collection<T>> {

    @Override
    default IIngredientType<T> getIngredientType() {
        return this;
    }

    default void register(IModIngredientRegistration registration) {
        registration.register(this, get(), this, this);
    }
}
