package pl.acmc.media.commands.cmds.staff.kick;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;
import pl.acmc.media.utils.ChatUtil;

import java.util.StringJoiner;

import static pl.acmc.media.Main.globalConfiguration;

@Permission("media.kick")
@Command(name = "kick")
public class KickPlayerCommand {

    @Execute
    void executeKickPlayer(@Context Player player, @Arg("nick") Player target) {
        var targetName = target.getName();
        if(target == null) {
            Messager.send(player, "Gracz {NICK} jest offline".replace("{NICK}", targetName), MessagerType.ERROR);
        }

        StringJoiner joiner = new StringJoiner("\n");
        for (String reason : globalConfiguration.kickReason) {
            joiner.add(reason);
        }

        for(Player allAdm : Bukkit.getOnlinePlayers()) {
            if(allAdm.hasPermission("media.kick.notify")) {
                Messager.send(allAdm, "Gracz {NICK} został wyrzucony.".replace("{NICK}", targetName), MessagerType.SUCCESS);
            }
        }
        target.kickPlayer(ChatUtil.coloredHex(joiner.toString()));

    }

}
