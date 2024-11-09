package lol.vifez.hub.config;

import lombok.Data;
import xyz.mkotb.configapi.comment.Comment;

@Data
public class LanguageConfig {

    @Comment("Built-in queue messages")
    private String joinedQueue = "&eYou have joined the queue for &c%queue%&e.";
    private String alreadyInQueue = "&cYou are already in the queue.";
    private String notInQueue = "&cYou are not in a queue.";
    private String leftQueue = "&cYou have left the queue for &e%queue%&c.";
    private String queueNotFound = "&cQueue not found.";
    private String connectingToServer = "&eSending you to &c%queue%&e...";
    private String queueAlreadyPaused = "&cThe &f%queue% &cqueue is already paused.";
    private String queueNotPaused = "&cThe &f%queue% &cqueue is not paused.";
    private String queuePaused = "&eYou have &apaused&e the &c%queue%&e queue.";
    private String queueUnpaused = "&eYou have &cunpaused&e the &c%queue%&e queue.";

    @Comment({"", "Build Messages"})
    private String buildModeEnabled = "&eYou are &anow&e in &cbuild mode&e.";
    private String buildModeDisabled = "&eYou are &cno longer&e in &cbuild mode&e.";

    @Comment({"", "Spawn messages"})
    private String setSpawn = "&eSet the spawn location to &c%player_x%&e, &c%player_y%&e, &c%player_z%&e.";

}