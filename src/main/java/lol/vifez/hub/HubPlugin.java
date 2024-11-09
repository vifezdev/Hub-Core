package lol.vifez.hub;

import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import lol.vifez.hub.util.CC;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;
import lol.vifez.hub.command.BuildCommand;
import lol.vifez.hub.command.HubCommands;
import lol.vifez.hub.config.*;
import lol.vifez.hub.listener.*;
import lol.vifez.hub.profile.ProfileHandler;
import lol.vifez.hub.queue.IQueueHandler;
import lol.vifez.hub.rank.HubRankHandler;
import lol.vifez.hub.scoreboard.HubScoreboardAdapter;
import lol.vifez.hub.selector.SelectorListener;
import lol.vifez.hub.util.BungeeUtil;
import lol.vifez.hub.util.assemble.Assemble;
import xyz.mkotb.configapi.ConfigFactory;

@Getter
@Setter
public class HubPlugin extends JavaPlugin {

    private HubConfig hubConfig;
    private LanguageConfig languageConfig;
    private ScoreboardConfig scoreboardConfig;

    private Assemble assemble;
    private HubRankHandler rankHandler;
    private IQueueHandler queueHandler;
    private ProfileHandler profileHandler;
    private PaperCommandManager commandManager;

    private long timeTracker;

    private final ConfigFactory configFactory = ConfigFactory.newFactory(this);

    @Override
    public void onEnable() {
        timeTracker = System.currentTimeMillis();
        loadConfigs();

        this.rankHandler = new HubRankHandler(this);
        this.queueHandler = hubConfig.getQueueConfig().getType().provide();
        this.profileHandler = hubConfig.getDatabase().provideProfileHandler();

        if (scoreboardConfig.isEnabled())
            this.assemble = new Assemble(this, new HubScoreboardAdapter(this));

        registerListeners(
                new PvPListener(),
                new PreventionListeners(),
                new JoinListener(this),
                new QuitListener(this),
                new SelectorListener(this),
                new HubItemListener(this),
                new DoubleJumpListener(this)
        );

        this.commandManager = new PaperCommandManager(this);
        commandManager.enableUnstableAPI("help");
        commandManager.enableUnstableAPI("brigadier");

        commandManager.registerCommand(new HubCommands(this));
        commandManager.registerCommand(new BuildCommand(this));
        queueHandler.registerCommands(commandManager);

        commandManager.setFormat(
                MessageType.HELP,
                ChatColor.YELLOW, ChatColor.GREEN, ChatColor.GOLD
        );

        Messenger messenger = Bukkit.getMessenger();
        messenger.registerOutgoingPluginChannel(this, "BungeeCord");
        messenger.registerIncomingPluginChannel(this, "BungeeCord", new BungeeUtil());

        BungeeUtil.updateServers();
        sendStartupMessage();
    }

    public void loadConfigs() {
        this.hubConfig = configFactory.fromFile("config", HubConfig.class);
        this.languageConfig = configFactory.fromFile("language", LanguageConfig.class);
        this.scoreboardConfig = configFactory.fromFile("scoreboard", ScoreboardConfig.class);
    }

    public void saveConfigs() {
        configFactory.save("config", hubConfig);
        configFactory.save("language", languageConfig);
        configFactory.save("scoreboard", scoreboardConfig);
    }

    @Override
    public void onDisable() {
        if (assemble != null) assemble.cleanup();
        queueHandler.shutdown();
    }

    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners)
            getServer().getPluginManager().registerEvents(listener, this);
    }

    private void sendStartupMessage() {
        logMessage("&7&m------------------------------");
        logMessage("&cvHub core");
        logMessage(" ");
        logMessage("&fVersion: &c" + getDescription().getVersion());
        logMessage("&fProtocol: &c" + getServer().getBukkitVersion());
        logMessage("&fAuthors: &c" + String.join(", ", getDescription().getAuthors()));
        logMessage(" ");
        logMessage("&fSpigot: &c" + getServer().getName());
        logMessage("&fLoaded in &c" + (System.currentTimeMillis() - timeTracker) + "ms");
        logMessage("&7&m------------------------------");
    }

    private void logMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(CC.translate(message));
    }

    public static HubPlugin get() {
        return JavaPlugin.getPlugin(HubPlugin.class);
    }
}