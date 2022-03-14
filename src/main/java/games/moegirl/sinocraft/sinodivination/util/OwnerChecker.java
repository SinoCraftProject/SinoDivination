package games.moegirl.sinocraft.sinodivination.util;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class OwnerChecker {

    @Nullable
    private UUID owner = null;
    private final Set<UUID> allowed = new HashSet<>();

    public boolean isAllowed(@Nullable Entity entity) {
        if (owner == null) {
            return true;
        }
        if (entity instanceof Player player) {
            UUID id = player.getGameProfile().getId();
            return owner.equals(id) || allowed.contains(id);
        }
        return false;
    }

    public void allow(@Nullable Entity entity) {
        UUID uuid = getUUID(entity);
        if (uuid != null) {
            allowed.add(uuid);
            setChanged();
        }
    }

    public void removeAllow(@Nullable Entity entity) {
        if (entity != null && allowed.remove(entity.getUUID())) {
            setChanged();
        }
    }

    public Set<UUID> getAllowed() {
        return Set.copyOf(allowed);
    }

    public void setOwner(@Nullable Entity placer) {
        owner = getUUID(placer);
        setChanged();
    }

    public void setOwner(@Nullable UUID owner) {
        this.owner = owner;
        setChanged();
    }

    @Nullable
    public UUID getOwner() {
        return owner;
    }

    @Nullable
    private UUID getUUID(@Nullable Entity entity) {
        if (entity instanceof Player player) {
            return player.getGameProfile().getId();
        } else if (entity instanceof OwnableEntity ownable) {
            return ownable.getOwnerUUID();
        } else if (entity instanceof AbstractHorse horse) {
            return horse.getOwnerUUID();
        }
        return null;
    }

    protected abstract void setChanged();

    public void save(CompoundTag tag) {
        if (owner != null) {
            tag.putUUID(SinoDivination.MOD_ID + ".owner", owner);
        }
        if (!allowed.isEmpty()) {
            ListTag list = new ListTag();
            allowed.stream().map(NbtUtils::createUUID).forEach(list::add);
            tag.put(SinoDivination.MOD_ID + ".allowed", list);
        }
    }

    public void load(CompoundTag tag) {
        if (tag.hasUUID(SinoDivination.MOD_ID + ".owner")) {
            owner = tag.getUUID(SinoDivination.MOD_ID + ".owner");
        }
        if (tag.contains(SinoDivination.MOD_ID + ".allowed", Tag.TAG_LIST)) {
            allowed.clear();
            tag.getList(SinoDivination.MOD_ID + ".allowed", Tag.TAG_INT_ARRAY).stream()
                    .map(NbtUtils::loadUUID)
                    .forEach(allowed::add);
        }
    }

}
