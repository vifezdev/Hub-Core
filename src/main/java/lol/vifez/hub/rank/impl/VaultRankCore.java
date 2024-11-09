package lol.vifez.hub.rank.impl;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import lol.vifez.hub.rank.data.HubRank;
import lol.vifez.hub.rank.IRankCore;

public class VaultRankCore implements IRankCore {

    private Chat chat = null;
    private Permission permission = null;

    public VaultRankCore() {
        setupChat();
        setupPermissions();
    }

    @Override
    public HubRank getRank(Player player) {
        String group = permission.getPrimaryGroup(player);
        return new HubRank(
                group.toLowerCase(),
                group,
                "",
                chat.getPlayerPrefix(player),
                0
        );
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServicesManager().getRegistration(Permission.class);
        permission = rsp.getProvider();
        return permission != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

}