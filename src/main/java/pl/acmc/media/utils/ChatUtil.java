package pl.acmc.media.utils;


import org.bukkit.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ChatUtil {
    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String colored(String message) {
        return coloredHex(message);
    }

    public static List<String> colored(List<String> text) {
        return text.stream().map(ChatUtil::colored).toList();
    }


    public static String coloredHex(String message) {
        Matcher matcher = pattern.matcher(message);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String hexCode = matcher.group();
            StringBuilder builder = new StringBuilder();
            builder.append("x");
            for (int i = 1; i < hexCode.length(); i++) {
                char c = hexCode.charAt(i);
                if (c == '&') {
                    builder.append("\u00a7");
                } else {
                    builder.append("&").append(c);
                }
            }
            matcher.appendReplacement(sb, builder.toString());
        }
        matcher.appendTail(sb);
        message = sb.toString();
        return ChatColor.translateAlternateColorCodes('&', message)
                .replace(">>", "»")
                .replace("<<", "«")
                .replace(":(", "☹")
                .replace("<3", "❤");
    }
}
