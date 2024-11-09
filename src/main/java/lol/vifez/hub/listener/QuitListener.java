package lol.vifez.hub.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.config.impl.ConnectionMessageConfig;
import lol.vifez.hub.util.placeholder.PlaceholderHandler;

@RequiredArgsConstructor
public class QuitListener implements Listener {

    private final HubPlugin plugin;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ConnectionMessageConfig config = plugin.getHubConfig().getLeaveMessage();

        event.setQuitMessage(!config.isEnabled()
                ? null
                : PlaceholderHandler.replace(player, config.getMessage())
        );
    }

}