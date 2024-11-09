package lol.vifez.hub.queue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.queue.builtin.handler.HubQueueHandler;
import lol.vifez.hub.queue.impl.PortalQueueHandler;
import lol.vifez.hub.util.Provider;

@Getter
@RequiredArgsConstructor
public enum QueueType {

    BUILT_IN(
            "lol.vifez.hub.HubPlugin",
            HubQueueHandler::new
    ),
    PORTAL(
            "me.joeleoli.portal.shared.queue.Queue",
            PortalQueueHandler::new
    );

    private final String packageName;
    private final Provider<IQueueHandler> provider;

    public IQueueHandler provide() {
        try {
            Class.forName(packageName);

            HubPlugin.get().getLogger().info("Using queue system: " + name());
        } catch (ClassNotFoundException e) {
            HubPlugin.get().getLogger().warning("Invalid queue system: "
                    + packageName + ". Defaulting to BUILT_IN.");
            return BUILT_IN.provide();
        }

        return provider.provide();
    }

}