package lol.vifez.hub.rank.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.rank.IRankCore;
import lol.vifez.hub.rank.impl.*;
import lol.vifez.hub.util.Provider;

@Getter
@RequiredArgsConstructor
public enum RankCoreType {

    DEFAULT("lol.vifez.hub.HubPlugin", DefaultRankCore::new),
    GLACIAL("me.vifez.core.Core", GlacialRankCore::new),
    VAULT("net.milkbowl.vault.permission.Permission", VaultRankCore::new),
    AQUA_CORE("me.activated.core.plugin.AquaCoreAPI", AquaCoreRankCore::new);

    private final String packageName;
    private final Provider<IRankCore> provider;

    public IRankCore provide() {
        try {
            Class.forName(packageName);

            HubPlugin.get().getLogger().info("Using rank core: " + name());
        } catch (ClassNotFoundException e) {
            HubPlugin.get().getLogger().warning("Invalid rank core: "
                    + packageName + ". Defaulting to DEFAULT.");
            return DEFAULT.provide();
        }

        return provider.provide();
    }

}