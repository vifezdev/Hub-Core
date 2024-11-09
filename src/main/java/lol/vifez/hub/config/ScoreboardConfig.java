package lol.vifez.hub.config;

import lombok.Data;
import xyz.mkotb.configapi.comment.Comment;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScoreboardConfig {

    @Comment("If the scoreboard is enabled or not")
    private boolean enabled = true;

    @Comment("The title of the scoreboard")
    private String title = "&c&lKrypton";

    @Comment({"",
            "The lines which always display",
            "%queue_board% will only display if the player is in a queue"})
    private List<String> lines = new ArrayList<String>() {{
        add("&7&m-------------------");
        add("&cOnline:");
        add("&f%online_count%");
        add("");
        add("&cRank:");
        add("&f%rank_color%%rank%");
        add("%queue_board%");
        add("");
        add("&cvifez.lol");
        add("&7&m-------------------");
    }};

    @Comment("The lines which display when in a queue")
    private List<String> queueLines = new ArrayList<String>() {{
        add("");
        add("&cQueue:");
        add("&f%queue%");
        add("&fPosition: &c#%queue_position% of %queue_size%");
    }};

}