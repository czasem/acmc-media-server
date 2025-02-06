package pl.acmc.media.commands.cmds.staff.chat;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;

@Permission("acmc.chat.command")
@Command(name = "chat")
public class ChatCommand {
    public static boolean chatStatus = false;

    @Execute
    void executeChat(@Context Player player) {
        Messager.send(player, "Poprawne użycie &4/chat [on/off/cc]", MessagerType.ERROR);
    }

    @Execute(name = "on")
    void executeChatOn(@Context Player player) {
        if(chatStatus) {
            Messager.send(player, "Chat jest już włączony", MessagerType.ERROR);
            return;
        }
        for(Player all : player.getServer().getOnlinePlayers()) {
            Messager.send(all, "Chat został &fwłączony", MessagerType.SUCCESS);
        }
        chatStatus = true;
    }

    @Execute(name = "off")
    void executeChatOff(@Context Player player) {
        if(!chatStatus) {
            Messager.send(player, "Chat jest już wyłączony", MessagerType.ERROR);
            return;
        }
        for(Player all : player.getServer().getOnlinePlayers()) {
            Messager.send(all, "Chat został &fwyłączony", MessagerType.SUCCESS);
        }
        chatStatus = false;
    }

    @Execute(name = "cc", aliases = {"clear"})
    void executeChatClear(@Context Player player) {
        for(Player all : player.getServer().getOnlinePlayers()) {
            for(int i = 0; i < 100; i++) {
                all.sendMessage("");
            }
            Messager.send(all, "Chat został &fwyczyszczony&a.", MessagerType.SUCCESS);
        }
    }

}
