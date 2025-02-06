package pl.acmc.media.commands.cmds.players.msg;

import java.util.Arrays;
import java.util.List;

public class MsgBlockedMessages {


    private static final List<String> blockedWords = Arrays.asList(
            "kurwa", "kurva", "hvj", "huj", "nigger", "cwel", "niga", "niger", "pedal",
            "pedał", "chuj", "chój", "hój", "pizda","%", "&", ">>", "<<", ".pl", ".eu", ".net", ".me",
            "cwl", "cvl", "wypierdalaj", "spierdalaj", "wypier", "japier", "jprdl", "jprl",
            "wojanmc", "tabmc", "rapy", "anarchia", ".gg", "clearmc", "mcstyles", "hypixel",
            "zielonemc", "craftcube"
    );

    private static final String ASCII_REGEX = "^[\\x00-\\x7F]*$";

    public static boolean containsBlockedWords(String message) {
        String lowerMessage = message.toLowerCase();
        for (String word : blockedWords) {
            if (lowerMessage.matches(".*" + word + ".*")) {
                return true;
            }
        }

        if (!message.matches(ASCII_REGEX)) {
            return true;
        }
        return false;


    }


}
