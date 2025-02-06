package pl.acmc.media.utils;

import org.bukkit.Bukkit;

public class ConsoleUtil {

    public static void execute(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
