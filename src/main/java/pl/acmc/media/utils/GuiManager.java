package pl.acmc.media.utils;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import static pl.acmc.media.Main.guiConfig;

public class GuiManager {

    private static final GuiItem fillItem = ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE)
            .setName(" ")
            .asGuiItem();

    public static Gui create(int rows, String title, boolean guiFill, boolean disableAllInteractions) {
        return create(rows, title, guiFill, disableAllInteractions, true);
    }

    public static Gui create(int rows, String title, boolean guiFill, boolean disableAllInteractions, boolean active) {
        Gui gui = Gui.gui()
                .title(Component.text(ChatUtil.coloredHex(title)))
                .rows(rows)
                .create();
        if (guiFill) {
            gui.getFiller().fill(fillItem);
        }
        if (disableAllInteractions) {
            gui.disableAllInteractions();
        }

        if (active) {
            guiConfig.getBackgrounds().get(rows).forEach(itemStack ->
                    itemStack.getSlots().forEach(slot -> gui.setItem(slot, ItemBuilder.from(itemStack.getItem()).asGuiItem(event -> {
                        //itemStack.execute((Player) event.getWhoClicked(), gui);
                    })))
            );
        }

        return gui;
    }

    public static PaginatedGui createPaginated(int rows, int pageSize, String title, boolean disableAllInteractions) {
        return createPaginated(rows, pageSize, title, disableAllInteractions, true);
    }

    public static PaginatedGui createPaginated(int rows, int pageSize, String title, boolean disableAllInteractions, boolean active) {
        PaginatedGui gui = Gui.paginated()
                .title(Component.text(ChatUtil.coloredHex(title)))
                .rows(rows)
                .pageSize(pageSize)
                .create();
        if (disableAllInteractions) {
            gui.disableAllInteractions();
        }

        if (active) {
            guiConfig.getBackgrounds().get(rows).forEach(itemStack ->
                    itemStack.getSlots().forEach(slot -> gui.setItem(slot, ItemBuilder.from(itemStack.getItem()).asGuiItem(event -> {
                        event.getWhoClicked().closeInventory();
                    })))
            );
        }

        return gui;
    }
}
