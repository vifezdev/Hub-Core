package lol.vifez.hub.config.impl;

import lombok.Data;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.queue.QueueType;
import xyz.mkotb.configapi.comment.Comment;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueueConfig {

    private boolean enabled = true;
    private String queueSystem = "BUILT_IN";

    @Comment({"These values only work for the builtin queue system.",
            "The delay between each queue tick in milliseconds."})
    private int queueDelay = 1000;
    @Comment("The queue names must match the server names in the BungeeCord config.")
    private List<String> queues = new ArrayList<String>() {{
        add("Kitmap");
    }};

    public QueueType getType() {
        try {
            return QueueType.valueOf(queueSystem);
        } catch (IllegalArgumentException e) {
            HubPlugin.get().getLogger().warning("Invalid queue system: "
                    + queueSystem + ". Defaulting to BUILT_IN.");
            return QueueType.BUILT_IN;
        }
    }

}