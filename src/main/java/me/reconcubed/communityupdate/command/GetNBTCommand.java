package me.reconcubed.communityupdate.command;

import com.google.common.base.Joiner;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.dimension.DimensionType;

public class GetNBTCommand implements Command<CommandSource> {
    private static final GetNBTCommand CMD = new GetNBTCommand();

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("nbt")
                .requires(cs -> cs.hasPermissionLevel(2))
                .executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource commandSource = context.getSource();
        if ((commandSource.getEntity() instanceof PlayerEntity)) {
            ItemStack heldItemMainhand = ((PlayerEntity) commandSource.getEntity()).getHeldItemMainhand();

            if (heldItemMainhand.hasTag()) {
                String nbt = heldItemMainhand.getTag().toString();
                context.getSource().sendFeedback(new TranslationTextComponent("command.communityupdate.getnbt", nbt), false);
            } else {
                context.getSource().sendFeedback(new TranslationTextComponent("command.communityupdate.getnbt.none"), false);
            }

        }
        return 1;
    }

}
