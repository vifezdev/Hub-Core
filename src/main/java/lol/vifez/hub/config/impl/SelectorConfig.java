package lol.vifez.hub.config.impl;

import lombok.Data;
import lol.vifez.hub.selector.SelectorItem;

import java.util.ArrayList;
import java.util.List;

@Data
public class SelectorConfig {

    private String title = "Server Selector";
    private int rows = 3;
    private List<SelectorItem> items = new ArrayList<SelectorItem>() {{
        add(new SelectorItem(
                "&c&lKitmap",
                13,
                new ArrayList<String>() {{
                    add("");
                    add("&eSeason: &c1");
                    add("&eOnline: &c%count_kitmap%");
                    add("");
                    add("&eClick to join the &cKitmap&e server!");
                }},
                "DIAMOND_SWORD",
                0,
                "Kitmap"
        ));
    }};

    public SelectorItem getItem(int slot) {
        for (SelectorItem item : items) {
            if (item.getSlot() == slot)
                return item;
        }

        return null;
    }

}