package lol.vifez.hub.util.placeholder;

import org.bukkit.entity.Player;

import java.util.List;

public interface PlaceholderAdapter {

    List<PlaceholderEntry> replace(Player player);

    default boolean playerRequired() {
        return true;
    }

}