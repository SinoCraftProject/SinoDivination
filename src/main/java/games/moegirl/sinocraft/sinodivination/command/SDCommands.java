package games.moegirl.sinocraft.sinodivination.command;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import games.moegirl.sinocraft.sinocore.api.SinoCoreAPI;
import games.moegirl.sinocraft.sinocore.api.command.CommandBuilder;
import games.moegirl.sinocraft.sinocore.api.command.CommandRegister;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.base.SimpleCropBlock;
import games.moegirl.sinocraft.sinodivination.blockentity.ICotinusEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.blockentity.SophoraChestEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.SophoraEntity;
import games.moegirl.sinocraft.sinodivination.tree.SDWoodwork;
import games.moegirl.sinocraft.sinodivination.util.OwnerChecker;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;
import java.util.function.BiConsumer;

@SuppressWarnings("DuplicatedCode")
public class SDCommands {

    public static final CommandRegister REGISTER = new CommandRegister();

    public static final int SEARCH_RANGE = 10;
    public static final String POS = "position";

    public static final String DEBUG = "debug";
    public static final String COTINUS = "cotinus";
    public static final String SOPHORA = "sophora";
    public static final String CROPS = "crops";

    static {
        REGISTER.register("sinodivination", rootBuilder -> rootBuilder
                .then(DEBUG)
                .requires(__ -> SinoCoreAPI.DEBUG)
                // SinoDivination debug Cotinus randomOwner [x, y, z]
                .then(COTINUS)
                .then("random_owner")
                .apply(b -> addPositionParameter(b, SDCommands::suggestCotinusEntity))
                .execute(context -> getCotinusOwner(context).setOwner(UUID.randomUUID()))
                // SinoDivination debug Cotinus setOwner [x, y, z]
                .forward(COTINUS)
                .then("set_owner")
                .apply(b -> addPositionParameter(b, SDCommands::suggestCotinusEntity))
                .execute(context -> getCotinusOwner(context).setOwner(context.getSource().getPlayerOrException()))
                // SinoDivination debug Cotinus removeOwner [x, y, z]
                .forward(COTINUS)
                .then("remove_owner")
                .apply(b -> addPositionParameter(b, SDCommands::suggestCotinusEntity))
                .execute(context -> getCotinusOwner(context).setOwner((UUID) null))
                // SinoDivination debug Cotinus addRecord [x, y, z]
                .forward(COTINUS)
                .then("add_record")
                .apply(b -> addPositionParameter(b, SDCommands::suggestCotinusEntity))
                .execute(context -> getCotinusOwner(context).allow(context.getSource().getPlayerOrException()))
                // SinoDivination debug Cotinus removeRecord [x, y, z]
                .forward(COTINUS)
                .then("remove_record")
                .apply(b -> addPositionParameter(b, SDCommands::suggestCotinusEntity))
                .execute(context -> getCotinusOwner(context).removeAllow(context.getSource().getPlayerOrException()))
                // SinoDivination debug Sophora randomOwner [x, y, z]
                .forward(DEBUG)
                .then(SOPHORA)
                .then("random_owner")
                .apply(b -> addPositionParameter(b, SDCommands::suggestSophoraDoor))
                .execute(context -> getSophoraDoor(context).setEntity(UUID.randomUUID()))
                // SinoDivination debug Sophora setOwner [x, y, z]
                .forward(SOPHORA)
                .then("set_owner")
                .apply(b -> addPositionParameter(b, SDCommands::suggestSophoraDoor))
                .execute(context -> getSophoraDoor(context).setEntity(context.getSource().getPlayerOrException()))
                // SinoDivination debug Sophora showChestRecords
                .forward(SOPHORA)
                .then("show_chest_records")
                .execute(SDCommands::showSophoraChests)
                // SinoDivination debug Crops grow [x, y, z]
                .forward(DEBUG)
                .then(CROPS)
                .then("grow")
                .apply(b -> addPositionParameter(b, SDCommands::suggestModCrop))
                .execute(c -> SDCommands.growCrop(c, 1))
                // SinoDivination debug Crops shrunk [x, y, z]
                .forward(CROPS)
                .then("shrunk")
                .apply(b -> addPositionParameter(b, SDCommands::suggestModCrop))
                .execute(c -> SDCommands.growCrop(c, -1))
        );
    }

    private static void addPositionParameter(CommandBuilder builder, BiConsumer<CommandContext<CommandSourceStack>, BiConsumer<BlockPos, String>> suggests) {
        builder.then(POS, BlockPosArgument.blockPos())
                .suggests((c, b) -> {
                    suggests.accept(c, (p, m) -> b.suggest(Long.toString(p.asLong()),
                            new LiteralMessage("[" + p.getX() + ", " + p.getY() + ", " + p.getZ() + "] " + m)));
                    return b.buildFuture();
                });
    }

    private static BlockPos getPositionParameter(CommandContext<CommandSourceStack> context) {
        return BlockPos.of(LongArgumentType.getLong(context, POS));
    }

    private static void suggestCotinusEntity(CommandContext<CommandSourceStack> context, BiConsumer<BlockPos, String> suggests) {
        ServerLevel level = context.getSource().getLevel();
        Vec3 position = context.getSource().getPosition();
        int hr = SEARCH_RANGE / 2;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(position.x - hr, position.y - hr, position.z - hr);
        for (int x = 0; x < SEARCH_RANGE; x++) {
            for (int y = 0; y < SEARCH_RANGE; y++) {
                for (int z = 0; z < SEARCH_RANGE; z++) {
                    BlockPos.MutableBlockPos p = pos.move(0, 0, 1);
                    if (level.isAreaLoaded(p, 1)) {
                        BlockState state = level.getBlockState(p);
                        String name;
                        if (state.is(SDWoodwork.COTINUS.door()) && state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
                            suggests.accept(p, "门");
                        } else if (state.is(SDWoodwork.COTINUS.trapdoor())) {
                            suggests.accept(p, "活板门");
                        } else if (state.is(SDWoodwork.COTINUS.fenceGate())) {
                            suggests.accept(p, "栅栏门");
                        }
                    }
                }
                pos.move(0, 1, 0);
            }
            pos.move(1, 0, 0);
        }
    }

    private static void suggestSophoraDoor(CommandContext<CommandSourceStack> context, BiConsumer<BlockPos, String> suggests) {
        ServerLevel level = context.getSource().getLevel();
        Vec3 position = context.getSource().getPosition();
        int hr = SEARCH_RANGE / 2;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(position.x - hr, position.y - hr, position.z - hr);
        for (int x = 0; x < SEARCH_RANGE; x++) {
            for (int y = 0; y < SEARCH_RANGE; y++) {
                for (int z = 0; z < SEARCH_RANGE; z++) {
                    BlockPos.MutableBlockPos p = pos.move(0, 0, 1);
                    if (level.isAreaLoaded(p, 1)) {
                        BlockState state = level.getBlockState(p);
                        if (state.is(SDWoodwork.SOPHORA.door()) && state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
                            suggests.accept(p, "门");
                        } else if (state.is(SDWoodwork.SOPHORA.trapdoor())) {
                            suggests.accept(p, "活板门");
                        } else if (state.is(SDWoodwork.SOPHORA.fenceGate())) {
                            suggests.accept(p, "栅栏门");
                        }
                    }
                }
                pos.move(0, 1, 0);
            }
            pos.move(1, 0, 0);
        }
    }

    private static void suggestModCrop(CommandContext<CommandSourceStack> context, BiConsumer<BlockPos, String> suggests) {
        ServerLevel level = context.getSource().getLevel();
        Vec3 position = context.getSource().getPosition();
        int hr = SEARCH_RANGE / 2;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(position.x - hr, position.y - hr, position.z - hr);
        for (int x = 0; x < SEARCH_RANGE; x++) {
            for (int y = 0; y < SEARCH_RANGE; y++) {
                for (int z = 0; z < SEARCH_RANGE; z++) {
                    BlockPos.MutableBlockPos p = pos.move(0, 0, 1);
                    if (level.isAreaLoaded(p, 1)) {
                        BlockState state = level.getBlockState(p);
                        if (SinoDivination.MOD_ID.equals(state.getBlock().getRegistryName().getNamespace())) {
                            if (state.getBlock() instanceof SimpleCropBlock<?> crop) {
                                suggests.accept(p, crop.getRegistryName().getPath() + " " + crop.getAge(state) + "/" + crop.getMaxAge());
                            }
                        }
                    }
                }
                pos.move(0, 1, 0);
            }
            pos.move(1, 0, 0);
        }
    }

    private static OwnerChecker getCotinusOwner(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel level = context.getSource().getLevel();
        BlockPos position = getPositionParameter(context);
        BlockEntity entity = level.getBlockEntity(position);
        if (entity instanceof ICotinusEntity ce) {
            return ce.owner();
        } else {
            LiteralMessage message = new LiteralMessage("Not found cotinus entity in " + position.toShortString());
            CommandExceptionType e = new SimpleCommandExceptionType(message);
            throw new CommandSyntaxException(e, message);
        }
    }

    private static SophoraEntity getSophoraDoor(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel level = context.getSource().getLevel();
        BlockPos position = getPositionParameter(context);
        BlockEntity entity = level.getBlockEntity(position);
        if (entity instanceof SophoraEntity se) {
            return se;
        } else {
            LiteralMessage message = new LiteralMessage("Not found sophora door entity in " + position.toShortString());
            CommandExceptionType e = new SimpleCommandExceptionType(message);
            throw new CommandSyntaxException(e, message);
        }
    }

    private static void showSophoraChests(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ServerLevel level = source.getLevel();
        Vec3 position = source.getPosition();
        int hr = SEARCH_RANGE / 2;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(position.x - hr, position.y - hr, position.z - hr);
        for (int x = 0; x < SEARCH_RANGE; x++) {
            for (int y = 0; y < SEARCH_RANGE; y++) {
                for (int z = 0; z < SEARCH_RANGE; z++) {
                    BlockPos.MutableBlockPos p = pos.move(0, 0, 1);
                    if (level.isAreaLoaded(p, 1)) {
                        level.getBlockEntity(p, SDBlockEntities.SOPHORA_CHEST.get()).ifPresent(chest -> {
                            TextComponent title = new TextComponent(pos.toShortString() + ": ");
                            ((SophoraChestEntity) chest).getEntity().ifPresentOrElse(
                                    record -> source.sendSuccess(title.append(record.name()).append(new TextComponent(" - " + record.birthday())), true),
                                    () -> source.sendSuccess(title.append("No Record"), true)
                            );
                        });
                    }
                }
                pos.move(0, 1, 0);
            }
            pos.move(1, 0, 0);
        }
    }

    private static void growCrop(CommandContext<CommandSourceStack> context, int count) throws CommandSyntaxException {
        ServerLevel level = context.getSource().getLevel();
        BlockPos position = getPositionParameter(context);
        BlockState state = level.getBlockState(position);
        if (state.getBlock() instanceof SimpleCropBlock<?> crop) {
            BlockState age = crop.getStateForAge(Mth.clamp(crop.getAge(state) + count, 0, crop.getMaxAge()));
            level.setBlock(position, age, 2);
        } else {
            LiteralMessage message = new LiteralMessage("Not found crop in " + position.toShortString());
            CommandExceptionType e = new SimpleCommandExceptionType(message);
            throw new CommandSyntaxException(e, message);
        }
    }
}
