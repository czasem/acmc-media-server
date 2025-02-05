package pl.acmc.media.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.acmc.media.Main;


import java.util.Random;

public class TrollUtil {

    public static void swap(Player player) {
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        ItemStack offHandItem = player.getInventory().getItemInOffHand();

        player.getInventory().setItemInOffHand(mainHandItem);
        player.getInventory().setItemInMainHand(offHandItem);
    }

    public static void setRandomPitchAndYaw(Player player) {
        Random random = new Random();

        float randomYaw = random.nextFloat() * 360 - 180;
        float randomPitch = random.nextFloat() * 180 - 90;

        var location = player.getLocation();
        location.setYaw(randomYaw);
        location.setPitch(randomPitch);

        player.teleport(location);
    }

    public static void random(Player player, int repetitions) {
        long interval = 5L;

        for (int i = 0; i < repetitions; i++) {
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                float newYaw2 = (player.getLocation().getYaw() + 180) % 360;
                player.getLocation().setYaw(newYaw2);
                player.teleport(player.getLocation());
                setRandomPitchAndYaw(player);
            }, interval * i);
        }
    }

}
