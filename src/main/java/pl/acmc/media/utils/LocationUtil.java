package pl.acmc.media.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;

import java.util.List;

public class  LocationUtil {
    public static boolean isInRadius(Location location, int radius) {
        return (Math.abs(location.getBlockX()) <= radius && Math.abs(location.getBlockZ()) <= radius);
    }

    public static boolean isInRadius(String location, Location to, int radius) {
        String[] split = location.split(";");
        if (!split[0].equals(to.getWorld().getName()))
            return false;
        int distanceX = Math.abs(to.getBlockX() - cordsToBlock(Double.parseDouble(split[1])));
        int distanceZ = Math.abs(to.getBlockZ() - cordsToBlock(Double.parseDouble(split[2])));
        return (distanceX <= radius && distanceZ <= radius);
    }

    public static boolean isInRadius(Location location, Location to, int radius) {
        if (!location.getWorld().getUID().equals(to.getWorld().getUID()))
            return false;
        int distanceX = Math.abs(to.getBlockX() - location.getBlockX());
        int distanceZ = Math.abs(to.getBlockZ() - location.getBlockZ());
        return (distanceX <= radius && distanceZ <= radius);
    }

    public static boolean isInRadius(Location location, Location to, int radius, int radiusY) {
        if (!location.getWorld().getUID().equals(to.getWorld().getUID()))
            return false;
        int distanceY = Math.abs(to.getBlockY() - location.getBlockY());
        int distanceX = Math.abs(to.getBlockX() - location.getBlockX());
        int distanceZ = Math.abs(to.getBlockZ() - location.getBlockZ());
        return (distanceX <= radius && distanceZ <= radius && distanceY <= radiusY);
    }

    public static boolean isSame(Location location, Location to) {
        return (location.getBlockX() == to.getBlockX() && location
                .getBlockY() == to.getBlockY() && location
                .getBlockZ() == to.getBlockZ());
    }

    public static Location deserialize(String customLocation) {
        String[] split = customLocation.split(";");
        World world = Bukkit.getWorld(split[0]);
        if (world == null) {
            throw new IllegalArgumentException("World not found: " + split[0]);
        }
        return new Location(world, Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }

    public static String serialize(Location location) {
        return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch();
    }

    public static String serializeCenter(Location location) {
        return location.getWorld().getName() + ";" + (location.getBlockX() + 0.5D) + ";" + location.getY() + ";" + (location.getBlockZ() + 0.5D) + ";" + location.getYaw() + ";" + location.getPitch();
    }

    public static String locationToString(Location location) {
        return location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();
    }

    public static Location locationFromString(String location) {
        String[] split = location.split(";");
        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
    }

    public static String customLocationToString(String location) {
        return location;
    }

    public static Location closestLocation(List<Location> locations, Location location) {
        double distance = -1.0D;
        Location finalLoc = null;
        for (Location l : locations) {
            if (finalLoc != null && Math.pow((location.getBlockX() - l.getBlockX()), 2.0D) + Math.pow((location.getBlockZ() - l.getBlockZ()), 2.0D) > distance)
                continue;
            finalLoc = l;
            distance = Math.pow((location.getBlockX() - l.getBlockX()), 2.0D) + Math.pow((location.getBlockZ() - l.getBlockZ()), 2.0D);
        }
        return finalLoc;
    }

    public static Location farthestLocation(List<Location> locations, Location location) {
        double distance = -1.0D;
        Location finalLoc = null;
        for (Location l : locations) {
            double pow = Math.pow((location.getBlockZ() - l.getBlockZ()), 2.0D);
            double pow1 = Math.pow((location.getBlockX() - l.getBlockX()), 2.0D);
            if (finalLoc != null && pow1 + pow < distance)
                continue;
            finalLoc = l;
            distance = Math.pow((location.getBlockX() - l.getBlockX()), 2.0D) + Math.pow((location.getBlockZ() - l.getBlockZ()), 2.0D);
        }
        return finalLoc;
    }

    public static Block findHighestBlock(World world, int X, int Z, int minY, int maxY) {
        int i = 1;
        while (true) {
            if (i < minY)
                return null;
            if (i > maxY)
                return null;
            Block block = (new Location(world, X, i, Z)).getBlock();
            if (block.getType().equals(Material.AIR))
                return block;
            i++;
        }
    }

    public static int cordsToBlock(double cord) {
        if (cord >= 0.0D)
            return (int)cord;
        return (int)cord - 1;
    }

    public static String toStringClaim(Location location) {
        return location.getWorld().getName() + ";" + (location.getBlockX() >> 4);
    }

    public static String toStringClaim(String location) {
        String[] split = location.split(";");
        return split[0] + ";" + (cordsToBlock(Double.parseDouble(split[1])) >> 4);
    }

    public static void clearItemsInWorld(World world) {
        world.getEntities().stream()
                .filter(entity -> entity instanceof Item)
                .forEach(entity -> entity.remove());
    }

    public static boolean isWithinBounds(Location loc, Location bound1, Location bound2) {
        if (!loc.getWorld().equals(bound1.getWorld()) || !loc.getWorld().equals(bound2.getWorld())) {
            return false;
        }

        double minX = Math.min(bound1.getX(), bound2.getX());
        double maxX = Math.max(bound1.getX(), bound2.getX());
        double minY = Math.min(bound1.getY(), bound2.getY());
        double maxY = Math.max(bound1.getY(), bound2.getY());
        double minZ = Math.min(bound1.getZ(), bound2.getZ());
        double maxZ = Math.max(bound1.getZ(), bound2.getZ());

        return loc.getX() >= minX && loc.getX() <= maxX &&
                loc.getY() >= minY && loc.getY() <= maxY &&
                loc.getZ() >= minZ && loc.getZ() <= maxZ;
    }

    public static Block getRandomBlock(World world, int minX, int maxX, int minZ, int maxZ, int minY, int maxY) {
        int x = MathUtil.getRandInt(minX, maxX);
        int z = MathUtil.getRandInt(minZ, maxZ);

        return findHighestBlock(world, x, z, minY, maxY);
    }
}
