package lol.vifez.hub.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.queue.builtin.HubQueue;
import lol.vifez.hub.util.placeholder.PlaceholderHandler;
import lol.vifez.hub.util.CC;

@CommandAlias("queue")
@CommandPermission("hub.queue.manage")
@RequiredArgsConstructor
public class QueueCommands extends BaseCommand {

    private final HubPlugin plugin;

    @Default
    @HelpCommand
    public void onHelp(CommandSender sender, CommandHelp help) {
        sender.sendMessage(CC.translate("&7&m------------------------------"));
        sender.sendMessage(CC.translate("&c&lVifez Hub Core &7- [&cv1.0&7]"));
        sender.sendMessage(CC.translate("&fAuthors: &cvifez &7(Krypton Development)"));
        sender.sendMessage(CC.translate("&7&m------------------------------"));
        sender.sendMessage(CC.translate("&cQueue commands"));
        sender.sendMessage(CC.translate("&7* &c/queue pause <queue> &7- &fPause the specified queue"));
        sender.sendMessage(CC.translate("&7* &c/queue unpause <queue> &7- &fUnpause the specified queue"));
        sender.sendMessage(CC.translate("&7&m------------------------------"));
    }

    @Subcommand("pause")
    @CommandCompletion("@queues")
    public void pause(CommandSender sender, HubQueue queue) {
        if (queue.isPaused()) {
            sender.sendMessage(PlaceholderHandler.tempReplace(
                    plugin.getLanguageConfig().getQueueAlreadyPaused(),
                    "queue",
                    queue.getTargetServer()
            ));
            return;
        }

        queue.setPaused(true);
        sender.sendMessage(PlaceholderHandler.tempReplace(
                plugin.getLanguageConfig().getQueuePaused(),
                "queue",
                queue.getTargetServer()
        ));
    }

    @Subcommand("unpause")
    @CommandCompletion("@queues")
    public void unpause(CommandSender sender, HubQueue queue) {
        if (!queue.isPaused()) {
            sender.sendMessage(PlaceholderHandler.tempReplace(
                    plugin.getLanguageConfig().getQueueNotPaused(),
                    "queue",
                    queue.getTargetServer()
            ));
            return;
        }

        queue.setPaused(false);
        sender.sendMessage(PlaceholderHandler.tempReplace(
                plugin.getLanguageConfig().getQueueUnpaused(),
                "queue",
                queue.getTargetServer()
        ));
    }
}