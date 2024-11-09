package lol.vifez.hub.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.queue.builtin.handler.HubQueueHandler;
import lol.vifez.hub.scoreboard.HubScoreboardAdapter;
import lol.vifez.hub.selector.menu.SelectorInventory;
import lol.vifez.hub.util.BungeeUtil;
import lol.vifez.hub.util.CC;
import lol.vifez.hub.util.assemble.Assemble;
import lol.vifez.hub.util.placeholder.PlaceholderHandler;
import lol.vifez.hub.util.serialize.SerializedLocation;

@CommandAlias("hub")
@RequiredArgsConstructor
public class HubCommands extends BaseCommand {

    private final HubPlugin plugin;

    @Default
    @HelpCommand
    public void onHelp(CommandSender sender, CommandHelp help) {
        sender.sendMessage(CC.translate("&7&m------------------------------"));
        sender.sendMessage(CC.translate("&c&lVifez Hub Core &7- [&cv1.0&7]"));
        sender.sendMessage(CC.translate("&fAuthors: &cvifez &7(Krypton Development)"));
        sender.sendMessage(CC.translate("&7&m------------------------------"));
        sender.sendMessage(CC.translate("&cUseful commands"));
        sender.sendMessage(CC.translate("&7* &c/hub reload &7- &fReload the cores configurations"));
        sender.sendMessage(CC.translate("&7* &c/hub selector &7- &fOpen the server selector"));
        sender.sendMessage(CC.translate("&7* &c/hub setspawn &7- &fSet the hubs spawn"));
        sender.sendMessage(CC.translate("&7&m------------------------------"));
    }

    @Subcommand("selector")
    @CommandPermission("hub.command.selector")
    public void selector(Player player) {
        player.openInventory(SelectorInventory.get().getInventory());
    }

    @Subcommand("setspawn")
    @CommandPermission("hub.command.setspawn")
    public void setSpawn(Player player) {
        plugin.getHubConfig().setSpawnLocation(
                new SerializedLocation(player.getLocation())
        );

        plugin.saveConfigs();
        player.sendMessage(PlaceholderHandler.replace(
                player,
                plugin.getLanguageConfig().getSetSpawn()
        ));
    }

    @Subcommand("reload")
    @CommandPermission("hub.command.reload")
    public void execute(CommandSender sender) {
        long startedAt = System.currentTimeMillis();
        boolean previousScoreboard = plugin.getScoreboardConfig().isEnabled();

        plugin.loadConfigs();
        plugin.getRankHandler().reloadRankCore();

        if (plugin.getQueueHandler() instanceof HubQueueHandler)
            ((HubQueueHandler) plugin.getQueueHandler()).loadQueues();

        boolean newScoreboard = plugin.getScoreboardConfig().isEnabled();
        if (previousScoreboard != newScoreboard) {
            if (newScoreboard) {
                plugin.setAssemble(new Assemble(
                        plugin,
                        new HubScoreboardAdapter(plugin)
                ));
            } else plugin.getAssemble().cleanup();
        }

        new SelectorInventory();
        BungeeUtil.updateServers();

        sender.sendMessage(CC.format(
                "&eReloaded configurations in &c%sms&e.",
                System.currentTimeMillis() - startedAt
        ));
    }

}