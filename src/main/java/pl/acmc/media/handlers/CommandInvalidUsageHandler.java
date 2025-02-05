package pl.acmc.media.handlers;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;
import pl.acmc.media.utils.ChatUtil;

public class CommandInvalidUsageHandler implements InvalidUsageHandler<CommandSender> {

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        CommandSender sender = invocation.sender();
        Schematic schematic = result.getSchematic();

        if (schematic.isOnlyFirst()) {
            sender.sendMessage(ChatUtil.coloredHex("&#FF0000:c &cPoprawne użycie: &#FF0000" + schematic.first()));
            return;
        }

        sender.sendMessage(ChatUtil.coloredHex("&#FF0000:c &cPoprawne użycie:" + schematic));
    }

}
