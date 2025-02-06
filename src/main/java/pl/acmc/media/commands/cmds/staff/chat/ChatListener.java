package pl.acmc.media.commands.cmds.staff.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(ChatCommand.chatStatus) {
            return;
        } else {
            if(player.hasPermission("acmc.chat.bypass")) {
                return;
            }
            event.setCancelled(true);
            Messager.send(player, "Chat jest wyłączony.", MessagerType.ERROR);
        }
    }
}
