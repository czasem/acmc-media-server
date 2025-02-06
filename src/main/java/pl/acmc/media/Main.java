package pl.acmc.media;

import dev.rollczi.litecommands.LiteCommands;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import pl.acmc.media.api.builders.CompleteBuilder;
import pl.acmc.media.configuration.GlobalConfiguration;
import pl.acmc.media.configuration.GuiConfig;
import pl.acmc.media.features.spawn.SpawnConfig;
import pl.acmc.media.features.warps.WarpConfig;
import pl.acmc.media.scheduler.Scheduler;
import pl.acmc.media.utils.ConfigUtil;

public class Main extends JavaPlugin {
    @Getter
    private static Main instance;

    @Getter
    private static final Scheduler scheduler = new Scheduler();

    @Getter
    public static GlobalConfiguration globalConfiguration;
    @Getter
    public static GuiConfig guiConfig;
    @Getter
    public static SpawnConfig spawnConfig;

    @Getter
    public static WarpConfig warpConfig;

    private LiteCommands<CommandSender> liteCommands;


    @Override
    public void onEnable() {
        instance = this;

        globalConfiguration = ConfigUtil.loadConfig(GlobalConfiguration.class);
        warpConfig = ConfigUtil.loadConfig(WarpConfig.class);
        guiConfig = ConfigUtil.loadConfig(GuiConfig.class);
        spawnConfig = ConfigUtil.loadConfig(SpawnConfig.class);

        CompleteBuilder.build(liteCommands);

    }


    public static void sendLog(String string) {
        System.out.println("[MEDIA] -> " + string);
    }
}