package pl.acmc.media.utils;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import pl.acmc.media.Main;
import pl.acmc.media.files.PaperSerdes;


import java.io.File;

public class ConfigUtil {

    public static <T extends OkaeriConfig> T loadConfig(Class<T> clazz) {
        return ConfigManager.create(clazz, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new PaperSerdes());
            it.withBindFile(new File(Main.getInstance().getDataFolder(), clazz.getSimpleName().toLowerCase().replace("config", "")+".yml"));
            it.saveDefaults();
            it.load(true);
        });
    }


}
