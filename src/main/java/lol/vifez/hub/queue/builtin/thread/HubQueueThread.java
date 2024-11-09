package lol.vifez.hub.queue.builtin.thread;

import lol.vifez.hub.queue.builtin.HubQueue;
import lol.vifez.hub.queue.builtin.handler.HubQueueHandler;
import lombok.RequiredArgsConstructor;
import lol.vifez.hub.HubPlugin;

@RequiredArgsConstructor
public class HubQueueThread extends Thread {

    private final HubPlugin plugin;
    private final HubQueueHandler handler;

    @Override
    public void run() {
        try {
            while (true) {
                for (HubQueue queue : handler.getQueues()) {
                    queue.tick();

                    queue.getInQueue().forEach(player -> {
                        if (player.isOnline())
                            return;

                        queue.getInQueue().remove(player);
                    });
                }

                Thread.sleep(plugin.getHubConfig().getQueueConfig().getQueueDelay());
            }
        } catch (Exception exception) {
            if (!(exception instanceof InterruptedException))
                exception.printStackTrace();
        }
    }

}