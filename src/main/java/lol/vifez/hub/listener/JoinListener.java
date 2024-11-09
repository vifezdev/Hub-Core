package lol.vifez.hub.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.config.HubConfig;
import lol.vifez.hub.config.impl.ConnectionMessageConfig;
import lol.vifez.hub.profile.HubProfile;
import lol.vifez.hub.util.Tasks;
import lol.vifez.hub.util.placeholder.PlaceholderHandler;

@RequiredArgsConstructor
public class JoinListener implements Listener {

    private final HubPlugin plugin;

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        Tasks.runAsync(() -> {
            HubProfile profile = plugin.getProfileHandler().loadProfile(event.getUniqueId());
            profile.setName(event.getName());

            plugin.getProfileHandler().saveProfile(profile, false);
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        HubConfig config = plugin.getHubConfig();

        spawnPlayer(player);

        ConnectionMessageConfig joinConfig = config.getJoinMessage();
        event.setJoinMessage(!joinConfig.isEnabled()
                ? null
                : PlaceholderHandler.replace(player, joinConfig.getMessage())
        );
    }

    public static void spawnPlayer(Player player) {
        HubConfig config = HubPlugin.get().getHubConfig();

        player.setExp(0);
        player.setLevel(0);

        player.setFoodLevel(20);
        player.setSaturation(20);

        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();

        config.getItems().forEach(hubItem -> player.getInventory().setItem(
                hubItem.getSlot(),
                hubItem.toItem()
        ));

        player.updateInventory();

        if (config.getSpawnLocation() != null)
            player.teleport(config.getSpawnLocation().toLocation(true));

        if (player.hasMetadata("PVP"))
            player.removeMetadata("PVP", HubPlugin.get());
    }

}