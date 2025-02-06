package pl.acmc.media.features.spawn;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.cooldown.Cooldown;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;
import pl.acmc.media.utils.LocationUtil;
import pl.acmc.media.utils.TeleportManager;
import pl.acmc.media.utils.location.SerializableLocation;

import java.time.temporal.ChronoUnit;

import static pl.acmc.media.Main.spawnConfig;

@Command(name = "spawn")
public class SpawnCommand {

    @Execute(name = "set")
    @Permission("acmc.spawn.admin")
    public void spawnSet(@Context Player player) {
        Location playerLocation = player.getLocation();
        spawnConfig.spawnLocation = new SerializableLocation(playerLocation);
        spawnConfig.save();
        Messager.send(player, spawnConfig.messageWhenLocalizationSetted, spawnConfig.messageWhenLocalizationSettedMessagerType);
    }

    @Execute
    @Permission("acmc.spawn")
    @Cooldown(key = "spawn-cooldown", count = 5,unit = ChronoUnit.SECONDS, bypass = "acmc.spawn.bypass")
    public void spawnTeleport(@Context Player player) {
        TeleportManager.teleport(player.getUniqueId())
                .teleport(spawnConfig.spawnLocation.toLocation());
    }

    @Execute(name = "reloadconfig")
    @Permission("acmc.spawn.admin")
    public void reloadSpawnConfig(@Context Player player) {
        spawnConfig.load();
        Messager.send(player, "Pomyslnie przeladowano konfiguracje!", MessagerType.SUCCESS);
    }
}