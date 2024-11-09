package lol.vifez.hub.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.config.impl.DoubleJumpConfig;

@RequiredArgsConstructor
public class DoubleJumpListener implements Listener {

    private static final String METADATA = "doublejump";

    private final HubPlugin plugin;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE
                || !plugin.getHubConfig().getDoubleJumpConfig().isEnabled())
            return;

        Block block = player.getLocation().subtract(0.0, 0.1, 0.0).getBlock();
        if (block.getType() != Material.AIR && !player.isFlying())
            player.setAllowFlight(true);
    }

    @EventHandler
    public void onFly(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        DoubleJumpConfig config = plugin.getHubConfig().getDoubleJumpConfig();

        if (player.getGameMode() == GameMode.CREATIVE || !config.isEnabled())
            return;

        event.setCancelled(true);
        player.setAllowFlight(false);
        player.setFlying(false);

        Location location = player.getLocation();
        player.setVelocity(location.getDirection()
                .multiply(config.getLengthMultiplier())
                .setY(config.getHeightValue())
        );

        player.setMetadata(METADATA, new FixedMetadataValue(plugin, METADATA));
        player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () ->
                player.removeMetadata(METADATA, plugin), 40);

        player.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 20);
    }

    @EventHandler
    public void onToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        DoubleJumpConfig config = plugin.getHubConfig().getDoubleJumpConfig();

        if (player.isOnGround()
                || !player.hasMetadata(METADATA)
                || !config.isEnabled()
                || !config.isShiftBoostEnabled())
            return;

        player.removeMetadata(METADATA, plugin);
        player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);

        Location location = player.getLocation();
        player.setVelocity(location.getDirection().multiply(
                config.getShiftMultiplier()
        ));
    }

}