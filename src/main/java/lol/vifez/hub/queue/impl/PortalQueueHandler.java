package lol.vifez.hub.queue.impl;

import me.joeleoli.portal.shared.queue.Queue;
import org.bukkit.entity.Player;
import lol.vifez.hub.queue.IQueueHandler;

import java.util.UUID;

public class PortalQueueHandler implements IQueueHandler {

    @Override
    public String getQueue(Player player) {
        Queue queue = Queue.getByPlayer(player.getUniqueId());

        if (queue != null)
            return queue.getName();

        return null;
    }

    @Override
    public int getQueuePosition(Player player) {
        UUID uuid = player.getUniqueId();
        return Queue.getByPlayer(uuid).getPosition(uuid);
    }

    @Override
    public int getQueueSize(Player player) {
        return Queue.getByPlayer(
                player.getUniqueId()
        ).getPlayers().size();
    }

    @Override
    public void joinQueue(Player player, String queue) {
        player.performCommand("joinqueue " + queue);
    }

    @Override
    public void leaveQueue(Player player) {
        player.performCommand("leavequeue");
    }

}