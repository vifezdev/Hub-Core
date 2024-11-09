package lol.vifez.hub.util;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import lol.vifez.hub.HubPlugin;

import java.util.HashMap;
import java.util.Map;

public class BungeeUtil implements PluginMessageListener {

    public static final Map<String, Integer> COUNT_MAP = new HashMap<>();

    static {
        Bukkit.getScheduler().runTaskTimerAsynchronously(HubPlugin.get(), () -> {
            if (Bukkit.getOnlinePlayers().isEmpty())
                return;

            COUNT_MAP.keySet().forEach(BungeeUtil::sendRequest);
            sendRequest("ALL");
        }, 10L, 20L);
    }

    public static void sendToServer(Player player, String server) {
        ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
        dataOutput.writeUTF("Connect");
        dataOutput.writeUTF(server);
        player.sendPluginMessage(HubPlugin.get(), "BungeeCord", dataOutput.toByteArray());
    }

    public static void sendRequest(String server) {
        Bukkit.getOnlinePlayers().stream().findFirst().ifPresent(player -> {
            ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
            dataOutput.writeUTF("PlayerCount");
            dataOutput.writeUTF(server);
            player.sendPluginMessage(HubPlugin.get(), "BungeeCord", dataOutput.toByteArray());
        });
    }

    public static void updateServers() {
        for (String server : HubPlugin.get().getHubConfig().getServerCounts()) {
            COUNT_MAP.put(server, 0);
            sendRequest(server);
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord"))
            return;

        ByteArrayDataInput dataInput = ByteStreams.newDataInput(message);
        String subChannel = dataInput.readUTF();

        if (subChannel.equals("PlayerCount")) {
            String server = dataInput.readUTF();
            int count = dataInput.readInt();

            COUNT_MAP.put(server, count);
        }
    }

}