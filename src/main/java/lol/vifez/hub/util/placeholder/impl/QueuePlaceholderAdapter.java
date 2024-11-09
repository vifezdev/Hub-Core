package lol.vifez.hub.util.placeholder.impl;

import com.google.common.collect.Lists;
import org.bukkit.entity.Player;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.queue.IQueueHandler;
import lol.vifez.hub.util.placeholder.PlaceholderAdapter;
import lol.vifez.hub.util.placeholder.PlaceholderEntry;

import java.util.ArrayList;
import java.util.List;

public class QueuePlaceholderAdapter implements PlaceholderAdapter {

    private final HubPlugin plugin = HubPlugin.get();

    @Override
    public List<PlaceholderEntry> replace(Player player) {
        IQueueHandler handler = plugin.getQueueHandler();

        return !handler.isInQueue(player) ? new ArrayList<>() : Lists.newArrayList(
                new PlaceholderEntry("queue", handler.getQueue(player)),
                new PlaceholderEntry("queue_position", handler.getQueuePosition(player)),
                new PlaceholderEntry("queue_size", handler.getQueueSize(player))
        );
    }

}