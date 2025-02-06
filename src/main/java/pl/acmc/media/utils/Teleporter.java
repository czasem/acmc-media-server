package pl.acmc.media.utils;

import com.sk89q.worldguard.protection.flags.Flags;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.acmc.media.Main;
import pl.acmc.media.api.messager.Messager;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Teleporter {
    private final UUID uniqueId;
    private BukkitTask future;

    public Teleporter(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void teleport(Location location) {
        Player player = Bukkit.getPlayer(uniqueId);
        if (player == null) return;


        long startTime = System.currentTimeMillis();
        long requiredTime = startTime + 6000;
        Location startLocation = player.getLocation();
        AtomicInteger loop = new AtomicInteger();

        future = Main.getInstance() != null
                ? Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            if (!player.isOnline() || !startLocation.getWorld().equals(player.getLocation().getWorld()) ||
                    startLocation.distanceSquared(player.getLocation()) > 1) {
                try {
                    Messager.sendTitle(player, "&f", "&#E03232&l✖ &#E03232Teleportacja nieudana, przerwano!");
                } catch (Exception ignored) {}
                cancel();
                return;
            }

            if (player.hasPermission("server.teleport-bypass")) {
                teleportNow(location);
                cancel();
                return;
            }

            long timeRemaining = requiredTime - System.currentTimeMillis();
            if (timeRemaining <= 100) {
                teleportNow(location);
                cancel();
                return;
            }

            String timeFormat = TimeUtil.convertPolish(timeRemaining, true);
            Messager.sendTitle(player,
                    "&#FBEB08&#36F13B&l⚠ &8| &#FBEB08&#36F13B&lᴛ&#36F23F&lᴇ&#36F342&lʟ&#35F446&lᴇ&#35F549&lᴘ&#35F64D&lᴏ&#35F750&lʀ&#35F854&lᴛ&#35F957&lᴀ&#34FA5B&lᴄ&#34FB5E&lᴊ&#34FC62&lᴀ&r &8| &#FBEB08&#36F13B&l⚠",
                    "&8• &fᴛᴇʟᴇᴘᴏʀᴛᴀᴄᴊᴀ ɴᴀꜱᴛąᴘɪ ᴢᴀ: &#FBEB08&#36F13B&n" + timeFormat + "&f &8•");
            SoundsUtil.orb(player);

            loop.incrementAndGet();
        }, 0L, 20L) : null;
    }

    public void teleportNow(Location location) {
        Player player = Bukkit.getPlayer(uniqueId);
        if (player != null) {
            teleportNow(player, location);
        }
    }

    public void teleportNow(Player player, Location location) {
        Messager.sendTitle(player, "&f", "&#60FF00&l✔ &#FBEB08&#36F13BPrzeteleportowano pomyślnie!");
        player.teleport(location);
    }

    public void cancel() {
        Player player = Bukkit.getPlayer(uniqueId);
        if (player != null) {
            //Messager.sendTitle(player.getName(), "&f", msg);
            //TimedBarCreator.send(player, msg, "teleporter", 2500);
        }
        if (future != null) {
            future.cancel();
            future = null;
        }
    }

    public UUID getUniqueId() {
        return uniqueId;
    }
}
