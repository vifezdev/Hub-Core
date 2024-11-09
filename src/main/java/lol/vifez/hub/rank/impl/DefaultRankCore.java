package lol.vifez.hub.rank.impl;

import org.bukkit.entity.Player;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.rank.data.HubRank;
import lol.vifez.hub.rank.IRankCore;

public class DefaultRankCore implements IRankCore {

    private final HubPlugin plugin = HubPlugin.get();

    @Override
    public HubRank getRank(Player player) {
        return plugin.getHubConfig().getFallbackRank();
    }

}