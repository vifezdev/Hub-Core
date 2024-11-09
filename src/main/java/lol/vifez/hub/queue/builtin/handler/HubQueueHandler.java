package lol.vifez.hub.queue.builtin.handler;

import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.PaperCommandManager;
import lol.vifez.hub.queue.builtin.HubQueue;
import lol.vifez.hub.queue.builtin.thread.HubQueueThread;
import lombok.Getter;
import org.bukkit.entity.Player;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.command.QueueCommands;
import lol.vifez.hub.config.LanguageConfig;
import lol.vifez.hub.queue.IQueueHandler;
import lol.vifez.hub.util.placeholder.PlaceholderHandler;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HubQueueHandler implements IQueueHandler {

    private final HubPlugin plugin = HubPlugin.get();
    private final List<HubQueue> queues = new ArrayList<>();

    private final HubQueueThread queueThread;
    private final LanguageConfig language;

    public HubQueueHandler() {
        loadQueues();

        this.language = plugin.getLanguageConfig();
        this.queueThread = new HubQueueThread(plugin, this);
        queueThread.start();
    }

    @Override
    public void registerCommands(PaperCommandManager commandManager) {
        commandManager.getCommandCompletions().registerCompletion("queues", context -> {
            List<String> queueNames = new ArrayList<>();

            queues.forEach(queue -> queueNames.add(
                    queue.getTargetServer()
            ));

            return queueNames;
        });

        commandManager.getCommandContexts().registerContext(HubQueue.class, context -> {
            String firstArg = context.popFirstArg();
            HubQueue queue = getQueue(firstArg);

            if (queue == null)
                throw new InvalidCommandArgument("Could not find queue with the name " + firstArg);

            return queue;
        });

        commandManager.registerCommand(new QueueCommands(plugin));
    }

    @Override
    public String getQueue(Player player) {
        HubQueue queue = getByPlayer(player);

        if (queue == null)
            return null;

        return queue.getTargetServer();
    }

    @Override
    public int getQueuePosition(Player player) {
        return getByPlayer(player).getPosition(player);
    }

    @Override
    public int getQueueSize(Player player) {
        return getByPlayer(player).getInQueue().size();
    }

    @Override
    public void joinQueue(Player player, String queueName) {
        HubQueue queue = getQueue(queueName);

        if (queue == null) {
            player.sendMessage(PlaceholderHandler.replace(
                    player,
                    language.getQueueNotFound()
            ));
            return;
        }

        queue.join(player);
    }

    @Override
    public void leaveQueue(Player player) {
        HubQueue queue = getByPlayer(player);

        if (queue == null) {
            player.sendMessage(PlaceholderHandler.replace(
                    player,
                    language.getNotInQueue()
            ));
            return;
        }

        queue.leave(player);
    }

    public HubQueue getByPlayer(Player player) {
        for (HubQueue queue : queues) {
            if (queue.getInQueue().contains(player))
                return queue;
        }

        return null;
    }

    public HubQueue getQueue(String name) {
        for (HubQueue queue : queues) {
            if (queue.getTargetServer().equalsIgnoreCase(name))
                return queue;
        }

        return null;
    }

    @Override
    public void shutdown() {
        queueThread.interrupt();
    }

    public void loadQueues() {
        queues.clear();

        for (String queue : HubPlugin.get().getHubConfig().getQueueConfig().getQueues())
            queues.add(new HubQueue(queue));
    }

}