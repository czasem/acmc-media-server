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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Permission("acmc.msg.reply")
@Command(name = "r")
public class ReplyCommand {

    @Execute
    void executeReply(@Context Player player, @Join("wiadomość") String message) {
        UUID lastMessagedUUID = MsgCommand.getLastMessaged(player.getUniqueId());

        if (lastMessagedUUID == null) {
            Messager.send(player, "Nie masz komu odpowiedzieć", MessagerType.ERROR);
            return;
        }

        Player target = Bukkit.getPlayer(lastMessagedUUID);
        if (target == null || !target.isOnline()) {
            Messager.send(player, "Nie masz komu odpowiedzieć", MessagerType.ERROR);
            return;
        }
        if (MsgBlockedMessages.containsBlockedWords(message)) {
            Messager.send(player, "Twoja wiadomość zawiera niedozwolone słowo", MessagerType.ERROR);
            return;
        }
        Messager.send(player, "&8(&fTy &8→ &f{NICK}&8) >> &f{MESSAGE}".replace("{NICK}", target.getName()).replace("{MESSAGE}", message), MessagerType.MESSAGE);
        Messager.send(target, "&8(&f{NICK} &8→ &fTy&8) >> &f{MESSAGE}".replace("{NICK}", player.getName()).replace("{MESSAGE}", message), MessagerType.MESSAGE);

        MsgCommand.getLastMessaged(player.getUniqueId());
    }
}
