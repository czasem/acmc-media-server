package pl.acmc.media.commands.cmds.staff.teleport;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;

@Permission("acmc.tphere")
@Command(name = "tphere", aliases = {"s"})
public class TpHereCommand {

    @Execute
    void executeTp(@Context Player player, @Arg("nick") Player target) {
        target.teleport(player);
        Messager.send(player, "Przeteleportowałeś gracza &f" + target.getName() + "&a do siebie.", MessagerType.SUCCESS);
    }

}
