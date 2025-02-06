package pl.acmc.media.commands.cmds.staff.teleport;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;

@Permission("acmc.tp")
@Command(name = "tp")
public class TpCommand {

    @Execute
    void executeTp(@Context Player player, @Arg("nick") Player target) {
        player.teleport(target);
        Messager.send(player, "Przeteleportowałeś się do &f" + target.getName() + "&a.", MessagerType.SUCCESS);
    }

}
