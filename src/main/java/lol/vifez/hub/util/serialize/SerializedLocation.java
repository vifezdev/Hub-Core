package lol.vifez.hub.util.serialize;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@Data
@NoArgsConstructor
public class SerializedLocation {

    private String world;
    private int x;
    private int y;
    private int z;
    private int yaw;
    private int pitch;

    public SerializedLocation(Location location) {
        this.world = location.getWorld().getName();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.yaw = (int) location.getYaw();
        this.pitch = (int) location.getPitch();
    }

    public Location toLocation(boolean centered) {
        return new Location(
                Bukkit.getWorld(world),
                x + (centered ? 0.5 : 0),
                y,
                z + (centered ? 0.5 : 0),
                yaw, pitch
        );
//        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

}