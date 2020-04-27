package me.reconcubed.communityupdate.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public class GetElytraColourCommand implements Command<CommandSource> {
    private static final GetElytraColourCommand CMD = new GetElytraColourCommand();

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("elytracolour")
                .requires(cs -> cs.hasPermissionLevel(2))
                .executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource commandSource = context.getSource();

        if ((commandSource.getEntity() instanceof PlayerEntity)) {
            ItemStack heldItemMainhand = ((PlayerEntity) commandSource.getEntity()).getHeldItemMainhand();

            if (heldItemMainhand.hasTag() && heldItemMainhand.getTag() != null && heldItemMainhand.getTag().contains("BlockEntityTag")) {
                Integer nbt = heldItemMainhand.getTag().getCompound("BlockEntityTag").getInt("Base");
                DyeColor color = DyeColor.byId(nbt);
                context.getSource().sendFeedback(new TranslationTextComponent("command.communityupdate.getnbt", color.getTranslationKey()), false);
            } else {
                context.getSource().sendFeedback(new TranslationTextComponent("nuffing"), false);
            }

        }
        return 1;
    }

}
