package lol.vifez.hub.util.placeholder;

import lombok.Getter;

@Getter
public class PlaceholderEntry {

    private final String identifier;
    private final String value;

    public PlaceholderEntry(String identifier, String value) {
        this.identifier = "%" + identifier + "%";
        this.value = value;
    }

    public PlaceholderEntry(String identifier, int value) {
        this(identifier, String.valueOf(value));
    }

}