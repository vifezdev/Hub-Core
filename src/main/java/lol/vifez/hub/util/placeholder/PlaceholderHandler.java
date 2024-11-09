package lol.vifez.hub.util.placeholder;

import org.bukkit.entity.Player;
import lol.vifez.hub.util.CC;
import lol.vifez.hub.util.placeholder.impl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaceholderHandler {

    private static final List<PlaceholderAdapter> ADAPTERS = new ArrayList<>();

    static {
        registerAdapters(
                new DefaultPlaceholderAdapter(),
                new QueuePlaceholderAdapter(),
                new CountPlaceholderAdapter()
        );
    }

    public static void registerAdapters(PlaceholderAdapter... adapters) {
        ADAPTERS.addAll(Arrays.asList(adapters));
    }

    public static String tempReplace(String input, String identifier, String value) {
        return CC.translate(input.replaceAll(
                "%" + identifier + "%",
                value
        ));
    }

    public static String replace(Player player, String input) {
        return replace(player, input, true);
    }

    public static String replace(Player player, String input, boolean translate) {
        for (PlaceholderAdapter adapter : ADAPTERS) {
            if (adapter.playerRequired() && player == null)
                continue;

            List<PlaceholderEntry> replace = adapter.replace(player);

            for (PlaceholderEntry entry : replace)
                input = input.replace(entry.getIdentifier(), entry.getValue());
        }

        return translate ? CC.translate(input) : input;
    }

    public static List<String> replace(Player player, List<String> input) {
        List<String> output = new ArrayList<>();

        for (String line : input)
            output.add(replace(player, line, true));

        return output;
    }

}
