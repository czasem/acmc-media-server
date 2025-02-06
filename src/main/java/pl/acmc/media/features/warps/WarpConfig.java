package pl.acmc.media.features.warps;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class WarpConfig extends OkaeriConfig {

    @Comment("Warpy")
    private final Map<String, WarpProcessor> warps = new HashMap<>();
}
