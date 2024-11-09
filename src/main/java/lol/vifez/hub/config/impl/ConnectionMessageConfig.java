package lol.vifez.hub.config.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionMessageConfig {

    private boolean enabled;
    private String message;

}