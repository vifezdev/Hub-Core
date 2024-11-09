package lol.vifez.hub.selector;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.config.HubConfig;
import lol.vifez.hub.config.impl.SelectorConfig;

@RequiredArgsConstructor
public class SelectorListener implements Listener {

    private final HubPlugin plugin;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        SelectorConfig config = plugin.getHubConfig().getSelectorConfig();

        if (!event.getInventory().getTitle().equals(config.getTitle())
                && !event.getWhoClicked().hasMetadata("Build")) {
            event.setCancelled(true);
            return;
        }

        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null
                || currentItem.getType() == Material.AIR
                || !currentItem.hasItemMeta()
                || !currentItem.getItemMeta().hasDisplayName())
            return;

        SelectorItem item = config.getItem(event.getSlot());
        if (item == null)
            return;

        plugin.getQueueHandler().joinQueue(
                (Player) event.getWhoClicked(),
                item.getServer()
        );

        event.setCancelled(true);
    }

}