package pl.acmc.media.commands.cmds.staff.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;
import pl.acmc.media.commands.cmds.players.msg.MsgBlockedMessages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().toLowerCase();

        if (ChatCommand.chatStatus) {
            if (MsgBlockedMessages.containsBlockedWords(message)) {
                Messager.send(player, "Twoja wiadomość zawiera niedozwolone słowo", MessagerType.ERROR);
                event.setCancelled(true);
                return;
            }

            if (!checkMessage(message)) {
                Messager.send(player, "Twoja wiadomość zawiera niedozwolone znaki", MessagerType.ERROR);
                event.setCancelled(true);
                return;
            }
            return;
        } else {
            if (player.hasPermission("acmc.chat.bypass")) {
                return;
            }
            event.setCancelled(true);
            Messager.send(player, "Chat jest wyłączony.", MessagerType.ERROR);
        }
    }


    public boolean checkMessage(String message) {
        String chat = message;
        boolean found = false;

        Pattern p = Pattern.compile("^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ0-9\\s!@#$%^&*()_+\\-=\\?':;,.<>\\[\\]{}|`~&]*$");
        Matcher m = p.matcher(chat);

        if (m.matches()) {
            found = true;
        }

        return found;
    }

}
