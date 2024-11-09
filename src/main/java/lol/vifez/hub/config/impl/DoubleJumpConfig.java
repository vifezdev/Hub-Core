package lol.vifez.hub.config.impl;

import lombok.Data;
import xyz.mkotb.configapi.comment.Comment;

@Data
public class DoubleJumpConfig {

    @Comment("Whether double jump is enabled or not")
    private boolean enabled = true;
    private double heightValue = 1;
    private double lengthMultiplier = 1.5;
    private boolean shiftBoostEnabled = true;
    private double shiftMultiplier = 3;

}