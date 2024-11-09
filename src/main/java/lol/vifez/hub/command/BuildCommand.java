package lol.vifez.hub.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.config.LanguageConfig;
import lol.vifez.hub.util.placeholder.PlaceholderHandler;

@RequiredArgsConstructor
public class BuildCommand extends BaseCommand {

    private final HubPlugin plugin;

    @CommandAlias("build")
    @CommandPermission("hub.command.build")
    public void execute(Player player) {
        LanguageConfig languageConfig = plugin.getLanguageConfig();

        if (player.hasMetadata("Build")) {
            player.removeMetadata("Build", plugin);
        } else {
            player.setMetadata("Build", new FixedMetadataValue(plugin, true));
        }

        player.sendMessage(PlaceholderHandler.replace(player, player.hasMetadata("Build")
                ? languageConfig.getBuildModeEnabled()
                : languageConfig.getBuildModeDisabled()
        ));
    }

}