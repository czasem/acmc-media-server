package pl.acmc.media.utils;

import org.bukkit.event.Listener;
import pl.acmc.media.Main;


import java.util.concurrent.TimeUnit;

public class Loader {
    public static void loadListener(Listener listener) {
        Main.getInstance().getServer().getPluginManager().registerEvents(listener, Main.getInstance());
        Main.sendLog("Loaded listener: "+listener.getClass().getSimpleName());
    }

    public static void loadScheduler(Runnable runnable, Long delayer, TimeUnit timeUnit) {
        loadScheduler(runnable, delayer, timeUnit, Main.getInstance().getName());
    }

    public static void loadScheduler(Runnable runnable, Long delayer, TimeUnit timeUnit, String name) {
        Main.getScheduler().registerScheduleAtFixedRate(name, runnable, delayer, delayer, timeUnit);
        Main.sendLog("Loaded scheduler: "+runnable.getClass().getSimpleName());
    }
}
