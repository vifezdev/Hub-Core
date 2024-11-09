package lol.vifez.hub.scoreboard;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.config.ScoreboardConfig;
import lol.vifez.hub.queue.IQueueHandler;
import lol.vifez.hub.util.CC;
import lol.vifez.hub.util.assemble.AssembleAdapter;
import lol.vifez.hub.util.placeholder.PlaceholderHandler;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HubScoreboardAdapter implements AssembleAdapter {

    private final HubPlugin plugin;

    @Override
    public String getTitle(Player player) {
        return CC.translate(plugin.getScoreboardConfig().getTitle());
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();

        ScoreboardConfig config = plugin.getScoreboardConfig();
        IQueueHandler queueHandler = plugin.getQueueHandler();

        for (String line : config.getLines()) {
            if (line.contains("%queue_board%")) {
                if (!queueHandler.isInQueue(player))
                    continue;

                lines.addAll(config.getQueueLines());
            } else lines.add(CC.translate(line));
        }

        return PlaceholderHandler.replace(player, lines);
    }

}
