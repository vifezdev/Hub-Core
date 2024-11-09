package lol.vifez.hub.rank;

import org.bukkit.entity.Player;
import lol.vifez.hub.rank.data.HubRank;

public interface IRankCore {

    HubRank getRank(Player player);

}