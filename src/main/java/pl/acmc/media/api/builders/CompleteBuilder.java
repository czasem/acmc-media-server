package pl.acmc.media.api.builders;

import dev.rollczi.litecommands.LiteCommands;
import org.bukkit.command.CommandSender;
import pl.acmc.media.api.builders.others.ListenersBuilder;
import pl.acmc.media.api.builders.others.LiteCommandsCustomBuilder;
import pl.acmc.media.api.builders.others.SchedulersBuilder;


public class CompleteBuilder {
    public static void build(LiteCommands<CommandSender> liteCommands) {

        ListenersBuilder.build();
        SchedulersBuilder.build();
        LiteCommandsCustomBuilder.build(liteCommands);

    }
}
