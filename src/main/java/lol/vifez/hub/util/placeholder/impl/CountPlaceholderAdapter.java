package lol.vifez.hub.util.placeholder.impl;

import org.bukkit.entity.Player;
import lol.vifez.hub.util.BungeeUtil;
import lol.vifez.hub.util.placeholder.PlaceholderAdapter;
import lol.vifez.hub.util.placeholder.PlaceholderEntry;

import java.util.ArrayList;
import java.util.List;

public class CountPlaceholderAdapter implements PlaceholderAdapter {

    @Override
    public List<PlaceholderEntry> replace(Player player) {
        List<PlaceholderEntry> entries = new ArrayList<>();

        for (String server : BungeeUtil.COUNT_MAP.keySet())
            entries.add(new PlaceholderEntry(
                    "count_" + server.toLowerCase(),
                    getCount(server)
            ));

        entries.add(new PlaceholderEntry(
                "count_all",
                getCount("ALL")
        ));

        return entries;
    }

    public int getCount(String server) {
        return BungeeUtil.COUNT_MAP.getOrDefault(server, 0);
    }

    @Override
    public boolean playerRequired() {
        return false;
    }
}