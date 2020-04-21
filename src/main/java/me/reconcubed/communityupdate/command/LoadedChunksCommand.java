package me.reconcubed.communityupdate.command;

import com.google.common.base.Joiner;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;


public class LoadedChunksCommand implements Command<CommandSource> {

    private static final LoadedChunksCommand CMD = new LoadedChunksCommand();

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("loadedchunks")
                .requires(cs -> cs.hasPermissionLevel(2))
                .executes(CMD);
    }
    // TODO Add check for Landmark in chunks
    //  GUI opens instead of sendFeedback with option to teleport directly to Landmark
    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource commandSource = context.getSource();
        DimensionType dimensionType = commandSource.func_197023_e().getDimension().getType();

        LongSet longSet = commandSource.getServer().func_71218_a(dimensionType).getForcedChunks();
        int i = longSet.size();

        if (i > 0) {
            String s = Joiner.on(", ").join(longSet.stream().sorted().map(ChunkPos::new).map(ChunkPos::toString).iterator());
            if (i == 1 ) {
                context.getSource().sendFeedback(new TranslationTextComponent("command.communityupdate.chunkload.single", dimensionType, s), false);
            } else {
                context.getSource().sendFeedback(new TranslationTextComponent("command.communityupdate.chunkload.multiple", i, dimensionType, s), false);
            }
        } else {
            context.getSource().sendFeedback(new TranslationTextComponent("command.communityupdate.chunkload.none", dimensionType), false);
        }
        return i;
    }
}
