package lol.vifez.hub.rank.impl;

import me.activated.core.api.rank.RankData;
import me.activated.core.plugin.AquaCoreAPI;
import org.bukkit.entity.Player;
import lol.vifez.hub.rank.data.HubRank;
import lol.vifez.hub.rank.IRankCore;

public class AquaCoreRankCore implements IRankCore {

    @Override
    public HubRank getRank(Player player) {
        RankData rank = AquaCoreAPI.INSTANCE.getPlayerRank(player.getUniqueId());

        return new HubRank(
                rank.getName(),
                rank.getPrefix(),
                rank.getSuffix(),
                "&" + rank.getColor().getChar(),
                rank.getWeight()
        );
    }

}