package pl.acmc.media;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.acmc.media.configuration.GlobalConfiguration;
import pl.acmc.media.scheduler.Scheduler;
import pl.acmc.media.utils.ConfigUtil;

public class Main extends JavaPlugin {
    @Getter
    private static Main instance;

    @Getter
    private static final Scheduler scheduler = new Scheduler();

    @Getter
    public static GlobalConfiguration globalConfiguration;

    @Override
    public void onEnable() {
        instance = this;

        globalConfiguration = ConfigUtil.loadConfig(GlobalConfiguration.class);

    }


    public static void sendLog(String string) {
        System.out.println("[MEDIA] -> " + string);
    }
}