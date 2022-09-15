package games.moegirl.sinocraft.sinodivination.menu;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.util.StringUtils;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SDMenus {

    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.CONTAINERS, SinoDivination.MODID);

    public static final RegistryObject<MenuType<SilkwormPlaqueMenu>> SILKWORM_PLAQUE = simple(SilkwormPlaqueMenu.class, SilkwormPlaqueMenu::new);

    public static final RegistryObject<MenuType<CarvingTableMenu>> CARVING_TABLE = simple(CarvingTableMenu.class, CarvingTableMenu::new);

    // =================================================================================================================

    public static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> simple(Class<T> aClass, MenuType.MenuSupplier<T> factory) {
        return REGISTRY.register(StringUtils.to_snake_name(aClass.getSimpleName()), () -> new MenuType<>(factory));
    }
}
