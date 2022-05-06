package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.capability.BirthdayData;
import games.moegirl.sinocraft.sinodivination.util.TagSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

public class SophoraChestEntity extends WoodenChestEntity implements ISophoraEntity {

    @Nullable
    private PlayerRecord entity;

    public SophoraChestEntity(BlockPos blockPos, BlockState state) {
        super(SDBlocks.SOPHORA_CHEST.get(), blockPos, state);
    }

    @Override
    public BlockEntity getSelf() {
        return this;
    }

    @Override
    public void setEntity(@Nullable Entity entity) {
        if (entity instanceof Player player && !SophoraEntity.holdGhostGuardStick(entity)) {
            this.entity = new PlayerRecord(player);
        }
    }

    public Optional<PlayerRecord> getEntity() {
        return Optional.ofNullable(entity);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        entity = pTag.contains(SinoDivination.MOD_ID + ".record", Tag.TAG_COMPOUND) ? new PlayerRecord(pTag.getCompound(SinoDivination.MOD_ID + ".record")) : null;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (entity != null) {
            pTag.put(SinoDivination.MOD_ID + ".record", entity.write());
        }
    }

    @Override
    public boolean handleGameEvent(Level pLevel, GameEvent pEvent, @Nullable Entity pEntity, BlockPos pPos) {
        if (pPos.equals(worldPosition) && pEvent.equals(GameEvent.CONTAINER_OPEN) && !SophoraEntity.holdGhostGuardStick(pEntity)) {
            setEntity(pEntity);
        }
        return false;
    }

    public record PlayerRecord(UUID uuid, Component name, LocalDateTime birthday) {

        public PlayerRecord(CompoundTag pTag) {
            this(pTag.getUUID("player"),
                    Optional.<Component>ofNullable(Component.Serializer.fromJson(pTag.getString("name")))
                            .orElseGet(() -> new TextComponent(pTag.getUUID("player").toString())),
                    LocalDateTime.of(LocalDate.ofEpochDay(pTag.getLong("date")),
                            LocalTime.ofNanoOfDay(pTag.getLong("time"))));
        }

        public PlayerRecord(Player player) {
            this(player.getUUID(), player.getDisplayName(),
                    player.getCapability(BirthdayData.CAPABILITY).resolve().orElseThrow().getBirthday());
        }

        public CompoundTag write() {
            CompoundTag record = new CompoundTag();
            record.putUUID("player", uuid);
            record.putString("name", Component.Serializer.toJson(name));
            TagSerializers.writeDate(birthday, record);
            return record;
        }
    }
}
