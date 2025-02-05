package pl.acmc.media.utils.location;

import org.bukkit.Location;

import java.io.Serializable;

public class SerializableLocation implements Serializable {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private String worldName;

    public SerializableLocation(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.worldName = location.getWorld().getName();
    }
    public Location toLocation() {
        return new Location(
                org.bukkit.Bukkit.getWorld(worldName),
                x, y, z, yaw, pitch
        );
    }
    @Override
    public String toString() {
        return "world=" + worldName + ", x=" + x + ", y=" + y + ", z=" + z + ", yaw=" + yaw + ", pitch=" + pitch;
    }

}
