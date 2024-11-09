package lol.vifez.hub.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import lol.vifez.hub.selector.menu.SelectorInventory;

import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public enum HubItemAction {

    ENDER_BUTT(player -> {
        if (player.getGameMode() == GameMode.CREATIVE)
            return;

        player.setVelocity(player.getLocation().getDirection().multiply(3.0f));
        player.updateInventory();
    }),

    SERVER_SELECTOR(player -> player.openInventory(
            SelectorInventory.get().getInventory()
    ));

    private final Consumer<Player> useConsumer;

}