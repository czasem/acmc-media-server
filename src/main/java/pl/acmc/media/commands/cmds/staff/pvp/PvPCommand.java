package pl.acmc.media.commands.cmds.staff.pvp;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;

@Permission("acmc.pvp.command")
@Command(name = "pvp")
public class PvPCommand {

    public static boolean pvpStatus = false;

    @Execute
    void executePvP(@Context Player player) {

        if (pvpStatus) {
            pvpStatus = false;
            for(Player all : Bukkit.getOnlinePlayers()) {
                Messager.send(all, "Bicie się między graczami zostało &fwyłączone&a!", MessagerType.SUCCESS);
            }
        } else {
            pvpStatus = true;
            for(Player all : Bukkit.getOnlinePlayers()) {
                Messager.send(all, "Bicie się między graczami zostało &fwłączone&a!", MessagerType.SUCCESS);
            }
        }



    }

}
