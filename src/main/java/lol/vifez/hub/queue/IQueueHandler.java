package lol.vifez.hub.queue;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public interface IQueueHandler {

    /**
     * Get the queue the player is in
     *
     * @param player the player
     * @return the queue the player is in
     */
    String getQueue(Player player);

    /**
     * Get the position of the player in the queue
     *
     * @param player the player
     * @return the position of the player in the queue
     */
    int getQueuePosition(Player player);

    /**
     * Get the size of the queue
     *
     * @param player the player
     * @return the size of the queue
     */
    int getQueueSize(Player player);

    /**
     * Join the player to a queue
     *
     * @param player the player
     * @param queue the queue
     */
    void joinQueue(Player player, String queue);

    /**
     * Leave the player from a queue
     *
     * @param player the player
     */
    void leaveQueue(Player player);

    /**
     * Check if the player is in a queue
     *
     * @param player the player
     * @return true if the player is in a queue, false otherwise
     */
    default boolean isInQueue(Player player) {
        return getQueue(player) != null;
    }

    /**
     * Shutdown the queue handler
     */
    default void shutdown() {}

    default void registerCommands(PaperCommandManager commandManager) {}

}