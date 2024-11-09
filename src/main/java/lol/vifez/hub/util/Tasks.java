package lol.vifez.hub.util;

import org.bukkit.Bukkit;
import lol.vifez.hub.HubPlugin;

public class Tasks {

    public static void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(
                HubPlugin.get(),
                runnable
        );
    }

    public static void runSync(Runnable runnable) {
        Bukkit.getScheduler().runTask(
                HubPlugin.get(),
                runnable
        );
    }

}