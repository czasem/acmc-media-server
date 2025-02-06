package pl.acmc.media.configuration;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import pl.acmc.media.files.entity.GuiItem;
import pl.acmc.media.utils.ItemStackBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GuiConfig extends OkaeriConfig {
    private Map<Integer, List<GuiItem>> backgrounds = new HashMap<>();

    public GuiConfig() {
        backgrounds.put(3, Arrays.asList(
                new GuiItem(
                        Arrays.asList(0, 8, 18, 26),
                        ItemStackBuilder.of(Material.YELLOW_STAINED_GLASS_PANE).get(),
                        Arrays.asList()
                )
        ));

        backgrounds.put(5, Arrays.asList(
                new GuiItem(
                        Arrays.asList(0, 8, 36, 44),
                        ItemStackBuilder.of(Material.YELLOW_STAINED_GLASS_PANE).get(),
                        Arrays.asList()
                )
        ));
    }
}
