package pl.acmc.media.commands.cmds.staff.broadcast;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.acmc.media.Main;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;
import pl.acmc.media.utils.ChatUtil;

@Permission("acmc.broadcast")
@Command(name = "broadcast", aliases = {"bc", "ogloszenie"})
public class BroadcastCommand {



    @Execute
    void executeBroadcast(@Context CommandSender sender) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatUtil.colored("&cPoprawne uzycie &4/bc [chat/rawchat/bar/title/subtitle/alert/all] [wiadomosc]"));
        } else {
            var player = (Player) sender;
            Messager.send(player, "Poprawne uzycie &4/bc [chat/bar/title/subtitle/alert/all] [wiadomosc]", MessagerType.ERROR);
        }
    }

    @Execute(name = "chat")
    void executeChatBroadcast(@Context CommandSender sender, @Join("wiadomość") String message) {
        if(!(sender instanceof Player)) {
            sendMessage(BroadcastEnum.CHAT, message);
            sender.sendMessage(ChatUtil.colored("&8>> &7Wysłałeś ogłoszenie &8(&a{OPTION}&8) &7do wszystkich graczy o treści: &a{MESSAGE}".replace("{OPTION}", "CHAT").replace("{MESSAGE}", message)));
            return;
        } else {
            var player = (Player) sender;
            Messager.send(player, "Wysłałeś ogłoszenie &8(&f{OPTION}&8) >> &f{WIADOMOSC}&a.".replace("{WIADOMOSC}", message).replace("{OPTION}", "CHAT"), MessagerType.SUCCESS);

            sendMessage(BroadcastEnum.CHAT, message);
        }
    }

    @Execute(name = "bar")
    void executeBarBroadcast(@Context CommandSender sender, @Join("wiadomość") String message) {
        if(!(sender instanceof Player)) {
            sendMessage(BroadcastEnum.BAR, message);
            sender.sendMessage(ChatUtil.colored("&8>> &7Wysłałeś ogłoszenie &8(&a{OPTION}&8) &7do wszystkich graczy o treści: &a{MESSAGE}".replace("{OPTION}", "ACTIONBAR").replace("{MESSAGE}", message)));
            return;
        } else {
            var player = (Player) sender;
            Messager.send(player, "Wysłałeś ogłoszenie &8(&f{OPTION}&8) >> &f{WIADOMOSC}&a.".replace("{WIADOMOSC}", message).replace("{OPTION}", "ACTIONBAR"), MessagerType.SUCCESS);

            sendMessage(BroadcastEnum.BAR, message);
        }
    }

    @Execute(name = "title")
    void executeTitleBroadcast(@Context CommandSender sender, @Join("wiadomość") String message) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatUtil.colored("&8>> &7Wysłałeś ogłoszenie &8(&a{OPTION}&8) &7do wszystkich graczy o treści: &a{MESSAGE}".replace("{OPTION}", "TITLE").replace("{MESSAGE}", message)));

            sendMessage(BroadcastEnum.TITLE, message);
            return;
        } else {
            var player = (Player) sender;
            Messager.send(player, "Wysłałeś ogłoszenie &8(&f{OPTION}&8) >> &f{WIADOMOSC}&a.".replace("{WIADOMOSC}", message).replace("{OPTION}", "TITLE"), MessagerType.SUCCESS);

            sendMessage(BroadcastEnum.TITLE, message);
        }
    }

    @Execute(name = "subtitle")
    void executeSubtitleBroadcast(@Context CommandSender sender, @Join("wiadomość") String message) {
        if(!(sender instanceof Player)) {
            sendMessage(BroadcastEnum.SUBTITLE, message);
            sender.sendMessage(ChatUtil.colored("&8>> &7Wysłałeś ogłoszenie &8(&a{OPTION}&8) &7do wszystkich graczy o treści: &a{MESSAGE}".replace("{OPTION}", "SUBTITLE").replace("{MESSAGE}", message)));
            return;
        } else {
            var player = (Player) sender;
            Messager.send(player, "Wysłałeś ogłoszenie &8(&f{OPTION}&8) >> &f{WIADOMOSC}&a.".replace("{WIADOMOSC}", message).replace("{OPTION}", "SUBTITLE"), MessagerType.SUCCESS);

            sendMessage(BroadcastEnum.SUBTITLE, message);
        }
    }

    @Execute(name = "alert")
    void executeAlertBroadcast(@Context CommandSender sender, @Join("wiadomość") String message) {
        if(!(sender instanceof Player)) {
            sendMessage(BroadcastEnum.ALERT, message);
            sender.sendMessage(ChatUtil.colored("&8>> &7Wysłałeś ogłoszenie &8(&a{OPTION}&8) &7do wszystkich graczy o treści: &a{MESSAGE}".replace("{OPTION}", "ALERT").replace("{MESSAGE}", message)));
            return;
        } else {
            var player = (Player) sender;
            Messager.send(player, "Wysłałeś ogłoszenie &8(&f{OPTION}&8) >> &f{WIADOMOSC}&a.".replace("{WIADOMOSC}", message).replace("{OPTION}", "ALERT"), MessagerType.SUCCESS);

            sendMessage(BroadcastEnum.ALERT, message);
        }
    }

    @Execute(name = "all")
    void executeAllBroadcast(@Context CommandSender sender, @Join("wiadomość") String message) {
        if(!(sender instanceof Player)) {
            sendMessage(BroadcastEnum.ALL, message);
            sender.sendMessage(ChatUtil.colored("&8>> &7Wysłałeś ogłoszenie &8(&a{OPTION}&8) &7do wszystkich graczy o treści: &a{MESSAGE}".replace("{OPTION}", "ALL").replace("{MESSAGE}", message)));
            return;
        } else {
            var player = (Player) sender;
            Messager.send(player, "Wysłałeś ogłoszenie &8(&f{OPTION}&8) >> &f{WIADOMOSC}&a.".replace("{WIADOMOSC}", message).replace("{OPTION}", "ALL"), MessagerType.SUCCESS);
            sendMessage(BroadcastEnum.ALL, message);
        }
    }

    @Execute(name = "rawchat")
    void executeRawChatBroadcast(@Context CommandSender sender, @Join("wiadomość") String message) {
        if(!(sender instanceof Player)) {
            sendMessage(BroadcastEnum.RAWCHAT, message);
            sender.sendMessage(ChatUtil.colored("&8>> &7Wysłałeś ogłoszenie &8(&a{OPTION}&8) &7do wszystkich graczy o treści: &a{MESSAGE}".replace("{OPTION}", "RAWCHAT").replace("{MESSAGE}", message)));
            return;
        } else {
            var player = (Player) sender;
            Messager.send(player, "Wysłałeś ogłoszenie &8(&f{OPTION}&8) >> &f{WIADOMOSC}&a.".replace("{WIADOMOSC}", message).replace("{OPTION}", "RAWCHAT"), MessagerType.SUCCESS);

            sendMessage(BroadcastEnum.RAWCHAT, message);
        }
    }


    private void sendMessage(BroadcastEnum option, String message) {
        for(Player all : Bukkit.getOnlinePlayers()) {
            switch (option) {
                case CHAT:
                    Bukkit.broadcastMessage(ChatUtil.colored("&4&lUWAGA &f" + message));
                    Main.sendLogBukkit("§8[§3ACMC-MEDIA§8] §7Broadcast §8(§f{OPTION}§8) §8»§f ".replace("{OPTION}", "CHAT") + message);

                    break;
                case BAR:
                    Messager.send(all, message, MessagerType.ACTIONBAR);
                    Main.sendLogBukkit("§8[§3ACMC-MEDIA§8] §7Broadcast §8(§f{OPTION}§8) §8»§f ".replace("{OPTION}", "BAR") + message);

                    break;
                case TITLE:
                    Messager.sendTitle(all, ChatUtil.colored(message), "");
                    Main.sendLogBukkit("§8[§3ACMC-MEDIA§8] §7Broadcast §8(§f{OPTION}§8) §8»§f ".replace("{OPTION}", "TITLE") + message);

                    break;
                case SUBTITLE:
                    Messager.send(all, "", MessagerType.SUBTITLE);
                    Main.sendLogBukkit("§8[§3ACMC-MEDIA§8] §7Broadcast §8(§f{OPTION}§8) §8»§f ".replace("{OPTION}", "SUBTITLE") + message);

                    break;
                case ALERT:
                    Messager.sendTitle(all, ChatUtil.colored("&4&lUWAGA"), ChatUtil.colored(message));
                    Main.sendLogBukkit("§8[§3ACMC-MEDIA§8] §7Broadcast §8(§f{OPTION}§8) §8»§f ".replace("{OPTION}", "ALERT") + message);

                    break;
                case ALL:
                    Main.sendLogBukkit("§8[§3ACMC-MEDIA§8] §7Broadcast §8(§f{OPTION}§8) §8»§f ".replace("{OPTION}", "ALL") + message);

                    sendMessage(BroadcastEnum.CHAT, message);
                    sendMessage(BroadcastEnum.BAR, message);
                    sendMessage(BroadcastEnum.TITLE, message);
                    sendMessage(BroadcastEnum.SUBTITLE, message);
                    sendMessage(BroadcastEnum.ALERT, message);
                    sendMessage(BroadcastEnum.RAWCHAT, message);
                    break;
                case RAWCHAT:
                    Main.sendLogBukkit("§8[§3ACMC-MEDIA§8] §7Broadcast §8(§f{OPTION}§8) §8»§f ".replace("{OPTION}", "RAWCHAT") + message);
                    Bukkit.broadcastMessage(ChatUtil.colored(message));
                    break;
            }
        }
    }
}
