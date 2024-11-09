package lol.vifez.hub.util.placeholder.impl;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.rank.data.HubRank;
import lol.vifez.hub.util.placeholder.PlaceholderAdapter;
import lol.vifez.hub.util.placeholder.PlaceholderEntry;

import java.util.List;

public class DefaultPlaceholderAdapter implements PlaceholderAdapter {

    private final HubPlugin plugin = HubPlugin.get();

    @Override
    public List<PlaceholderEntry> replace(Player player) {
        HubRank rank = plugin.getRankHandler().getRank(player);
        Location location = player.getLocation();

        return Lists.newArrayList(
                new PlaceholderEntry("player", player.getName()),
                new PlaceholderEntry("display_name", player.getDisplayName()),
                new PlaceholderEntry("online_count", Bukkit.getOnlinePlayers().size()),

                new PlaceholderEntry("rank", rank.getName()),
                new PlaceholderEntry("rank_color", rank.getColor()),
                new PlaceholderEntry("rank_prefix", rank.getPrefix()),

                new PlaceholderEntry("player_x", location.getBlockX()),
                new PlaceholderEntry("player_y", location.getBlockY()),
                new PlaceholderEntry("player_z", location.getBlockZ())
        );
    }

}