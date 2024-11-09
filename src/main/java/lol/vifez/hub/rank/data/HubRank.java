package lol.vifez.hub.rank.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HubRank {

    private String id;
    private String name;
    private String color;
    private String prefix;
    private int priority;

}