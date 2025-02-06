package pl.acmc.media.commands.cmds.staff.join;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.acmc.media.Main;
import pl.acmc.media.utils.ChatUtil;

import java.util.StringJoiner;

import static pl.acmc.media.Main.globalConfiguration;

public class JoinListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("acmc.join.bypass")) return;

        boolean joinStatus = globalConfiguration.join;

        if (!joinStatus) {
            StringJoiner joinStatusKick = new StringJoiner("\n");
            for (String reason : globalConfiguration.joinKickMessage) {
                joinStatusKick.add(reason);
            }

            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatUtil.coloredHex(joinStatusKick.toString()));
            return;
        }

        int maxSlots = globalConfiguration.maxSlots;
        if (Bukkit.getOnlinePlayers().size() >= maxSlots) {
            StringJoiner maxSlotsKick = new StringJoiner("\n");
            for (String reason : globalConfiguration.maxSlotsReason) {
                maxSlotsKick.add(reason);
            }

            event.disallow(PlayerLoginEvent.Result.KICK_FULL, ChatUtil.coloredHex(maxSlotsKick.toString()));
        }
    }
}
