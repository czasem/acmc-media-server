package pl.acmc.media.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

import java.util.ArrayList;
import java.util.List;

public class GlobalConfiguration extends OkaeriConfig {

    @Comment("Powód wyrzucenia gracza z trybu")
    public List<String> kickReason = new ArrayList<>();
}
