package pl.acmc.media.commands.cmds.staff.heal;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;

@Permission("acmc.heal")
@Command(name = "heal")
public class HealCommand {

    @Execute
    void executeHeal(@Context Player player) {
        healPlayer(player);
        Messager.send(player, "Zostałeś uleczony", MessagerType.SUCCESS);
    }

    @Execute
    void executeHeal(@Context Player player, @Arg("nick") String targetName) {
        if (targetName.equalsIgnoreCase("*")) {
            Bukkit.getOnlinePlayers().forEach(this::healPlayer);
            Messager.send(player, "Uleczyłeś wszystkich graczy", MessagerType.SUCCESS);
            return;
        }

        Player target = Bukkit.getPlayer(targetName);
        if (target == null || !target.isOnline()) {
            Messager.send(player, "Gracz {NICK} jest offline".replace("{NICK}", targetName), MessagerType.ERROR);
            return;
        }

        healPlayer(target);
        Messager.send(player, "Uleczyłeś gracza {NICK}".replace("{NICK}", targetName), MessagerType.SUCCESS);
    }

    private void healPlayer(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setSaturation(5.0f);
    }
}
