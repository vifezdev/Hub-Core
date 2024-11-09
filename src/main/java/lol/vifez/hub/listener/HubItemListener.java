package lol.vifez.hub.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.item.HubItem;

@RequiredArgsConstructor
public class HubItemListener implements Listener {

    private final HubPlugin plugin;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.hasItem() || !event.getAction().name().startsWith("RIGHT_CLICK"))
            return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || !item.hasItemMeta()
                || !item.getItemMeta().hasDisplayName())
            return;

        HubItem hubItem = plugin.getHubConfig().getItem(item);
        if (hubItem == null || hubItem.toAction() == null)
            return;

        hubItem.toAction().getUseConsumer().accept(player);

        event.setCancelled(true);
        event.setUseItemInHand(Event.Result.DENY);
        event.setUseInteractedBlock(Event.Result.DENY);
    }
}