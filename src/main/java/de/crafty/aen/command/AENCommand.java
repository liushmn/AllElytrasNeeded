package de.crafty.aen.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import de.crafty.aen.AllElytrasNeeded;
import de.crafty.aen.config.AENConfigs;
import de.crafty.aen.config.AbstractAENConfig;
import de.crafty.aen.config.SlotConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class AENCommand {


    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher){
        commandDispatcher.register(
                Commands.literal(AllElytrasNeeded.MOD_ID)
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(3))
                        .then(Commands.literal("reloadConfigs").executes(AENCommand::reloadConfigs))
        );

    }

    private static int reloadConfigs(CommandContext<CommandSourceStack> context){
        AENConfigs.all().forEach(AbstractAENConfig::load);
        context.getSource().sendSuccess(() -> Component.translatable("aen.command.reloaded"), true);
        return 1;
    }
}
