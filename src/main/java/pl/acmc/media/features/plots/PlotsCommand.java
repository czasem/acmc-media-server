package pl.acmc.media.features.plots;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.cooldown.Cooldown;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;

import java.time.temporal.ChronoUnit;

import static pl.acmc.media.Main.plotsConfig;

@Command(name = "plots")
@Permission("acmc.media.plots")
public class PlotsCommand {

    @Execute
    @Cooldown(key = "plots-cooldown", count = 5, unit = ChronoUnit.SECONDS, bypass = "acmc.media.plots.bypass")
    public void helpMessage(@Context Player player) {
        for (String message : plotsConfig.helpMessage) {
            Messager.send(player, message, MessagerType.MESSAGE);
        }
    }
}
