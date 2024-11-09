package lol.vifez.hub.config;

import lombok.Data;
import org.bukkit.inventory.ItemStack;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.config.impl.*;
import lol.vifez.hub.profile.database.DatabaseType;
import lol.vifez.hub.item.HubItem;
import lol.vifez.hub.rank.data.HubRank;
import lol.vifez.hub.rank.data.RankCoreType;
import lol.vifez.hub.util.serialize.SerializedLocation;
import xyz.mkotb.configapi.comment.Comment;

import java.util.ArrayList;
import java.util.List;

@Data
public class HubConfig {

    @Comment({"License key for product activation."})
    private String licenseKey = "XXXX-XXXX-XXXX-XXXX";

    @Comment({"The database type for profiles.", "Options: FLAT_FILE"})
    private String databaseType = "FLAT_FILE";

    @Comment({"", "Whether a message should be sent when a player joins."})
    private ConnectionMessageConfig joinMessage = new ConnectionMessageConfig(
            true,
            "&eWelcome &c%player% &eto the server!"
    );

    @Comment({"", "Whether a message should be sent when a player leaves."})
    private ConnectionMessageConfig leaveMessage = new ConnectionMessageConfig(
            true,
            "&c%player% &ehas left the server!"
    );

    @Comment({"", "Rank Cores: DEFAULT, VAULT, GLACIAL, AQUA_CORE"})
    private String rankCore = "DEFAULT";
    @Comment("The default rank to use.")
    private HubRank fallbackRank = new HubRank(
            "default",
            "Default",
            "&a",
            "&a" +
                    "" +
                    "Default",
            0
    );

    @Comment({"", "Double Jump Configuration"})
    private DoubleJumpConfig doubleJumpConfig = new DoubleJumpConfig();

    @Comment({
            "",
            "Queue Configuration",
            "Queue Systems: BUILT_IN, PORTAL"
    })
    private QueueConfig queueConfig = new QueueConfig();

    @Comment({"", "Server Selector Configuration"})
    private SelectorConfig selectorConfig = new SelectorConfig();

    @Comment({"", "List of servers to get player counts for.",
            "The name must be the same as the server name in BungeeCord."})
    private List<String> serverCounts = new ArrayList<String>() {{
        add("Kitmap");
    }};

    @Comment({"",
            "Hotbar Item Configuration",
            "Actions: SERVER_SELECTOR, ENDER_BUTT"
    })
    private List<HubItem> items = new ArrayList<HubItem>() {{
        add(new HubItem(
                "&eServer Selector",
                4,
                new ArrayList<>(),
                "COMPASS",
                0,
                "SERVER_SELECTOR"
        ));

        add(new HubItem(
                "&eEnder Pearl",
                0,
                new ArrayList<>(),
                "ENDER_PEARL",
                0,
                "ENDER_BUTT"
        ));
    }};

    private SerializedLocation spawnLocation;

    public DatabaseType getDatabase() {
        try {
            return DatabaseType.valueOf(databaseType);
        } catch (IllegalArgumentException e) {
            HubPlugin.get().getLogger().warning("Invalid database type: " + databaseType + ". Defaulting to FLAT_FILE.");
            return DatabaseType.FLAT_FILE;
        }
    }

    public RankCoreType getRankCore() {
        try {
            return RankCoreType.valueOf(rankCore);
        } catch (IllegalArgumentException e) {
            HubPlugin.get().getLogger().warning("Invalid rank core: "
                    + rankCore + ". Defaulting to DEFAULT.");
            return RankCoreType.DEFAULT;
        }
    }

    public HubItem getItem(ItemStack itemStack) {
        for (HubItem item : items) {
            if (item.toItem().isSimilar(itemStack))
                return item;
        }

        return null;
    }
}