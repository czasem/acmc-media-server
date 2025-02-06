package pl.acmc.media.files.entity;

import dev.triumphteam.gui.guis.Gui;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.acmc.media.utils.ConsoleUtil;

import java.util.List;

@Getter
@Setter
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class GuiItem extends OkaeriConfig {
    
    private List<Integer> slots;
    private ItemStack item;
    private List<String> actions;

    public GuiItem(List<Integer> slots, ItemStack item, List<String> actions) {
        this.slots = slots;
        this.item = item;
        this.actions = actions;
    }

    public void execute(Player player, Gui gui) {
        for (String action : actions) {
            if (action.startsWith("[chat]: ")) {
                String chat = action.substring("[chat]: ".length()).trim();
                player.chat(chat);
            } else if (action.startsWith("[sound]: ")) {
                Sound sound = Sound.valueOf(action.substring("[sound]: ".length()).trim());
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
            } else if (action.startsWith("[console]: ")) {
                String command = action.substring("[console]: ".length()).trim();
                ConsoleUtil.execute(command.replace("{PLAYER}", player.getName()));
            } else if (action.startsWith("[close]")) {
                gui.close(player);
            }
        }
    }
}
