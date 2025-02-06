package pl.acmc.media.features.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.acmc.media.Main;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;
import pl.acmc.media.commands.cmds.staff.chat.ChatCommand;
import pl.acmc.media.commands.cmds.staff.pvp.PvPCommand;
import pl.acmc.media.utils.ChatUtil;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoinAdmin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("acmc.join.info")) {
            Boolean joinStatus = Main.globalConfiguration.join;
            int maxSlots = Main.globalConfiguration.maxSlots;
            player.sendMessage(ChatUtil.coloredHex("&8&m-------------------------"));
            player.sendMessage(ChatUtil.coloredHex("&8"));
            player.sendMessage(ChatUtil.coloredHex("&8>> &7Chat: &f" + (ChatCommand.chatStatus ? "&aWłączony" : "&cWyłączony")));
            player.sendMessage(ChatUtil.coloredHex("&8>> &7PvP: &f" + (PvPCommand.pvpStatus ? "&aWłączone" : "&cWyłączone")));
            player.sendMessage(ChatUtil.coloredHex("&8>> &7Możliwość dołączenia: &f" + (joinStatus ? "&aTak" : "&cNie")));
            player.sendMessage(ChatUtil.coloredHex("&8>> &7Maksymalna ilośc slotów: &f" + maxSlots));
            // tu sie doda
            player.sendMessage(ChatUtil.coloredHex("&8>> &7Online: &f" + Bukkit.getOnlinePlayers().size()));
            player.sendMessage(ChatUtil.coloredHex("&8"));
            player.sendMessage(ChatUtil.coloredHex("&8&m-------------------------"));
        }
    }
}
