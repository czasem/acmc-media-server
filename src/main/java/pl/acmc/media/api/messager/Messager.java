package pl.acmc.media.api.messager;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.acmc.media.api.messager.type.MessagerType;
import pl.acmc.media.utils.ChatUtil;
import pl.acmc.media.utils.SoundsUtil;


public class Messager {

    public static void send(Player player, String message, MessagerType type) {
        if (player == null) {
            Bukkit.getConsoleSender().sendMessage(ChatUtil.colored("&cUser is null"));
            return;
        }

        String formattedMessage = ChatUtil.coloredHex(message);
        TextComponent textComponent = new TextComponent(formattedMessage);

        switch (type) {
            case SUCCESS -> {
                String format = ChatUtil.coloredHex("&#46F660✔ &8| &a" + message);
                SoundsUtil.orb(player);
                player.sendMessage(format);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(format));
            }
            case CHAT -> {
                SoundsUtil.orb(player);
                player.sendMessage(formattedMessage);
            }
            case ERROR -> {
                String format = ChatUtil.coloredHex("&#FF0029✖ &8| &c" + message);
                SoundsUtil.error(player);
                player.sendMessage(format);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(format));
            }
            case MESSAGE -> {
                SoundsUtil.orb(player);
                player.sendMessage(formattedMessage);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);
            }
            case ACTIONBAR -> {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);
            }
            case SUBTITLE -> {
                SoundsUtil.orb(player);
                player.sendTitle("", formattedMessage);
            }
        }
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        if (player == null) return;

        String formattedTitle = ChatUtil.coloredHex(title);
        String formattedSubtitle = ChatUtil.coloredHex(subtitle);

        player.sendTitle(formattedTitle, formattedSubtitle);
    }
}
