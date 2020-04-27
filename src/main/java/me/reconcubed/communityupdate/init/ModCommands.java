package me.reconcubed.communityupdate.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.reconcubed.communityupdate.CommunityUpdate;
import me.reconcubed.communityupdate.command.GetNBTCommand;
import me.reconcubed.communityupdate.command.LoadedChunksCommand;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ModCommands {
    public static void register(CommandDispatcher<CommandSource> dispatch) {
        LiteralCommandNode<CommandSource> commands = dispatch.register(
                Commands.literal(CommunityUpdate.MODID)
                    .then(LoadedChunksCommand.register(dispatch))
                    .then(GetNBTCommand.register(dispatch))
        );
        dispatch.register(Commands.literal("cu").redirect(commands));
    }

}
