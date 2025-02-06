package pl.acmc.media.api.builders.others;


import pl.acmc.media.commands.cmds.staff.chat.ChatListener;
import pl.acmc.media.commands.cmds.staff.join.JoinListener;
import pl.acmc.media.commands.cmds.staff.pvp.PvPListener;
import pl.acmc.media.features.listeners.PlayerJoinListener;
import pl.acmc.media.utils.Loader;

public class ListenersBuilder {
    public static void build() {
        Loader.loadListener(new PvPListener());
        Loader.loadListener(new ChatListener());
        Loader.loadListener(new JoinListener());
        Loader.loadListener(new PlayerJoinListener());
    }
}
