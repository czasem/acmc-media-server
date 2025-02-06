package pl.acmc.media.commands.cmds.players.msg;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;
import pl.acmc.media.utils.ChatUtil;

import java.util.*;

@Permission("acmc.msg")
@Command(name = "msg")
public class MsgCommand {

    private static final Map<UUID, UUID> lastMessaged = new HashMap<>();
    private static final Map<UUID, Boolean> msgToggled = new HashMap<>();
    private static final Map<UUID, Set<UUID>> ignoredPlayers = new HashMap<>();

    @Execute
    void executeMsg(@Context Player player, @Arg("nick") Player target, @Join("wiadomość") String message) {

        if(player == target) {
            Messager.send(player, "Nie możesz wysyłać wiadomości do siebie", MessagerType.ERROR);
            return;
        }

        if (!msgToggled.getOrDefault(target.getUniqueId(), true)) {
            Messager.send(player, "Nie możesz wysyłać wiadomości do &f{NICK}".replace("{NICK}", target.getName()), MessagerType.ERROR);
            return;
        }

        if (ignoredPlayers.getOrDefault(target.getUniqueId(), Collections.emptySet()).contains(player.getUniqueId())) {
            Messager.send(player, "Nie możesz wysyłać wiadomości do &f{NICK}".replace("{NICK}", target.getName()), MessagerType.ERROR);
            return;
        }


        if(!player.hasPermission("acmc.msg.bypass")) {
            if (MsgBlockedMessages.containsBlockedWords(message)) {
                Messager.send(player, "Twoja wiadomość zawiera niedozwolone słowo", MessagerType.ERROR);
                return;
            }
        }

        Messager.send(player, "&8(&fTy &8→ &f{NICK}&8) >> &f{MESSAGE}".replace("{NICK}", target.getName()).replace("{MESSAGE}", message), MessagerType.MESSAGE);
        Messager.send(target, "&8(&f{NICK} &8→ &fTy&8) >> &f{MESSAGE}".replace("{NICK}", player.getName()).replace("{MESSAGE}", message), MessagerType.MESSAGE);

        lastMessaged.put(player.getUniqueId(), target.getUniqueId());
        lastMessaged.put(target.getUniqueId(), player.getUniqueId());
    }

    @Execute(name = "ignore")
    void executeIgnore(@Context Player player, @Arg("nick") Player target) {

        if(player == target) {
            Messager.send(player, "Nie możesz ignorować siebie", MessagerType.ERROR);
            return;
        }
        ignoredPlayers.putIfAbsent(player.getUniqueId(), new HashSet<>());
        Set<UUID> ignored = ignoredPlayers.get(player.getUniqueId());

        if (ignored.contains(target.getUniqueId())) {
            ignored.remove(target.getUniqueId());
            Messager.send(player, "Już nie ignorujesz gracza &f{NICK}".replace("{NICK}", target.getName()), MessagerType.SUCCESS);
        } else {
            ignored.add(target.getUniqueId());
            Messager.send(player, "Od teraz ignorujesz gracza &f{NICK}".replace("{NICK}", target.getName()), MessagerType.SUCCESS);
        }
    }

    @Execute(name = "on")
    void executeMsgOn(@Context Player player) {
        msgToggled.put(player.getUniqueId(), true);
        Messager.send(player, "Włączyłeś prywatne wiadomości", MessagerType.SUCCESS);
    }

    @Execute(name = "off")
    void executeMsgOff(@Context Player player) {
        msgToggled.put(player.getUniqueId(), false);
        Messager.send(player, "Wyłączyłeś prywatne wiadomości", MessagerType.SUCCESS);
    }

    public static UUID getLastMessaged(UUID playerUUID) {
        return lastMessaged.get(playerUUID);
    }
}
