package lol.vifez.hub.profile;

import lombok.Data;

import java.util.UUID;

@Data
public class HubProfile {

    private final UUID uuid;
    private String name;

}