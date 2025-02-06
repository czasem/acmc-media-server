package pl.acmc.media.features.warps;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.acmc.media.Main;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;
import pl.acmc.media.utils.*;

public class WarpMenu {

    public static void open(Player player) {;
        Gui gui = GuiManager.create(5, ChatUtil.coloredHex("&8ᴡᴀʀᴘʏ ꜱᴇʀᴡᴇʀᴏᴡᴇ"), false, true, true);

        SoundsUtil.gui(player);

        Main.warpConfig.getWarps().forEach((warp, processor) -> {
            gui.setItem(processor.getSlot(), ItemBuilder.from(processor.getItemStack()).asGuiItem(event -> {
                if (player.hasPermission(processor.getPermission())) {
                    player.closeInventory();
                    TeleportManager.teleport(player.getUniqueId())
                            .teleport(LocationUtil.deserialize(processor.getLocation()));
                } else {
                    SoundsUtil.error(player);
                    Messager.send(player, "&cNie posiadasz wystarczających uprawnień &8(&f" + processor.getPermission() + "&8)", MessagerType.ERROR);
                }
            }));
        });

        gui.open(player);
    }
}
