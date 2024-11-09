package lol.vifez.hub.selector.menu;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.config.impl.SelectorConfig;
import lol.vifez.hub.util.CC;

@Getter
public class SelectorInventory {

    private static SelectorInventory instance;
    private final Inventory inventory;

    public SelectorInventory() {
        instance = this;

        SelectorConfig config = HubPlugin.get().getHubConfig().getSelectorConfig();
        this.inventory = Bukkit.createInventory(
                null,
                config.getRows() * 9,
                CC.translate(config.getTitle())
        );

        config.getItems().forEach(item -> inventory.setItem(
                item.getSlot(),
                item.toItem()
        ));
    }

    public static SelectorInventory get() {
        return instance == null
                ? new SelectorInventory()
                : instance;
    }

}