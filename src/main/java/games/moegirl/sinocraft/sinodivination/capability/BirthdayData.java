package games.moegirl.sinocraft.sinodivination.capability;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

public class BirthdayData implements ICapabilitySerializable<CompoundTag> {

    public static final ResourceLocation ID = new ResourceLocation(SinoDivination.MOD_ID, "birthday");

    public static final Capability<BirthdayData> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    private LocalDateTime birthday;

    public BirthdayData() {
        birthday = LocalDateTime.now();
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void from(Player original) {
        original.getCapability(CAPABILITY).ifPresent(d -> birthday = d.birthday);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("dateTime", birthday.toString());
        return null;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("dateTime", Tag.TAG_STRING)) {
            birthday = LocalDateTime.parse(nbt.getString("dateTime"));
        }
    }
}
