package lol.vifez.hub.profile;

import lombok.Getter;
import org.bukkit.entity.Player;
import lol.vifez.hub.HubPlugin;
import lol.vifez.hub.util.Tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public abstract class ProfileHandler {

    protected final HubPlugin plugin = HubPlugin.get();
    protected final Map<UUID, HubProfile> profileMap = new HashMap<>();

    /**
     * Load a profile from the storage
     *
     * @param uuid the UUID of the profile to load
     * @return the loaded profile
     */
    public abstract HubProfile loadProfile(UUID uuid);

    /**
     * Save a profile to the storage
     *
     * @param profile the profile to save
     */
    public abstract void save(HubProfile profile);

    /**
     * Get a profile from the cache or load it if it doesn't exist
     *
     * @param uuid the UUID of the profile to get
     * @return the profile
     */
    public HubProfile getProfile(UUID uuid) {
        return profileMap.getOrDefault(uuid, null);
    }

    /**
     * Get a profile from the cache or load it if it doesn't exist
     *
     * @param player the player of the profile to get
     * @return the profile
     */
    public HubProfile getProfile(Player player) {
        return getProfile(player.getUniqueId());
    }

    /**
     * Save a profile to the storage
     *
     * @param profile the profile to save
     * @param async   whether to save the profile asynchronously
     */
    public void saveProfile(HubProfile profile, boolean async) {
        if (async) {
            Tasks.runAsync(() -> saveProfile(profile, false));
            return;
        }

        save(profile);
    }

}