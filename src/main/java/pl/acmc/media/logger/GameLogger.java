package pl.acmc.media.logger;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import pl.acmc.media.logger.fantasy.FantasyLogResolver;
import pl.acmc.media.logger.fantasy.FantasyLogType;
import pl.acmc.media.utils.ChatUtil;


public class GameLogger {
    public static void fantasyLog(Object obj, FantasyLogType type) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        if (obj == null) {
            console.sendMessage("Object is null.");
            return;
        }

        Class<?> clazz = obj.getClass();
        String simpleName = clazz.getSimpleName().toLowerCase();

        String resolvedMessage = FantasyLogResolver.resolveMessage(type, simpleName);

        console.sendMessage(resolvedMessage);
    }


    public void log(String message) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        console.sendMessage(ChatUtil.coloredHex(message));
    }
}
