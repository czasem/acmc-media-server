package pl.acmc.media.commands.cmds.staff.gamemode;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;
import pl.acmc.media.utils.ChatUtil;


@Command(name = "gamemode", aliases = {"gm"})
@Permission("group.admin")
public class GameModeCommand {

    @Execute
    public void execute(@Context Player player, @Arg("gameMode") GameMode gameMode) {
        player.setGameMode(gameMode);
        Messager.send(player, "Zmieniono tryb gry na &f" + gameMode, MessagerType.SUCCESS);
    }

    @Execute
    public String execute(@Context CommandSender sender, @Arg("gameMode") GameMode gameMode, @Arg("target") Player target) {
        target.setGameMode(gameMode);
        Messager.send(target, "Zmieniono tryb gry na &f" + gameMode, MessagerType.SUCCESS);
        return ChatUtil.coloredHex("&aZmieniono tryb gry gracza &2" + target.getName() + " &ana &f" + gameMode);
    }
}
