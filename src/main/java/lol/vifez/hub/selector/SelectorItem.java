package lol.vifez.hub.selector;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import lol.vifez.hub.util.ItemBuilder;
import lol.vifez.hub.util.placeholder.PlaceholderHandler;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectorItem {

    private String displayName;
    private int slot;
    private List<String> lore;
    private String material;
    private int data;
    private String server;

    public ItemStack toItem() {
        Material material = Material.getMaterial(this.material);
        ItemBuilder itemBuilder = data == 0
                ? new ItemBuilder(material)
                : new ItemBuilder(material, (short) data);

        return itemBuilder.setName(displayName)
                .setLore(PlaceholderHandler.replace(null, lore))
                .build();
    }

}
