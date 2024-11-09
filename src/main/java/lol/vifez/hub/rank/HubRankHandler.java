package lol.vifez.hub.rank;

import lombok.Getter;
import org.bukkit.entity.Player;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.rank.data.HubRank;

@Getter
public class HubRankHandler {

    private final HubPlugin plugin;
    private IRankCore rankCore;

    public HubRankHandler(HubPlugin plugin) {
        this.plugin = plugin;

        reloadRankCore();
    }

    public HubRank getRank(Player player) {
        return rankCore.getRank(player);
    }

    public void reloadRankCore() {
        this.rankCore = plugin.getHubConfig().getRankCore().provide();
    }

}