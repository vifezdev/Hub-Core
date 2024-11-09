package lol.vifez.hub.queue.builtin;

import lombok.Data;
import org.bukkit.entity.Player;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.config.LanguageConfig;
import lol.vifez.hub.rank.HubRankHandler;
import lol.vifez.hub.util.BungeeUtil;
import lol.vifez.hub.util.placeholder.PlaceholderHandler;

import java.util.ArrayList;
import java.util.List;

@Data
public class HubQueue {
    
    private final LanguageConfig language 
            = HubPlugin.get().getLanguageConfig();

    private final String targetServer;
    private final List<Player> inQueue = new ArrayList<>();

    private boolean paused = false;

    public void join(Player player) {
        HubRankHandler rankHandler = HubPlugin.get().getRankHandler();

        if (inQueue.contains(player)) {
            player.sendMessage(PlaceholderHandler.replace(
                    player,
                    language.getAlreadyInQueue()
            ));
            return;
        }

        int playerRankPriority = rankHandler.getRank(player).getPriority();
        for (int i = 0; i < inQueue.size(); i++) {
            Player listPlayer = inQueue.get(i);
            if (rankHandler.getRank(listPlayer).getPriority() < playerRankPriority) {
                inQueue.add(i, player);

                player.sendMessage(PlaceholderHandler.replace(
                        player,
                        language.getJoinedQueue()
                ));
                return;
            }
        }

        inQueue.add(player);
        player.sendMessage(PlaceholderHandler.replace(
                player,
                language.getJoinedQueue()
        ));
    }

    public void leave(Player player) {
        inQueue.removeIf(listPlayer -> listPlayer.getUniqueId().equals(player.getUniqueId()));

        player.sendMessage(PlaceholderHandler.replace(
                player,
                language.getLeftQueue()
        ));
    }

    public void tick() {
        if (paused || inQueue.isEmpty())
            return;

        Player player = inQueue.get(0);
        player.sendMessage(PlaceholderHandler.replace(
                player,
                language.getConnectingToServer()
        ));

        BungeeUtil.sendToServer(player, targetServer);
        inQueue.remove(player);
    }

    public int getPosition(Player player) {
        return inQueue.indexOf(player) + 1;
    }

}