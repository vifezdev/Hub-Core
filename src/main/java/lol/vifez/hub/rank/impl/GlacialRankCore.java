package lol.vifez.hub.rank.impl;

import me.vifez.core.CoreAPI;
import me.vifez.core.rank.Rank;
import org.bukkit.entity.Player;
import lol.vifez.hub.rank.data.HubRank;
import lol.vifez.hub.rank.IRankCore;

public class GlacialRankCore implements IRankCore {

    @Override
    public HubRank getRank(Player player) {
        Rank rank = CoreAPI.getProfile(
                player.getUniqueId()
        ).getRank();

        return new HubRank(
                rank.getUUID().toString(),
                rank.getName(),
                rank.getColor(),
                rank.getPrefix(),
                rank.getPriority()
        );
    }

}