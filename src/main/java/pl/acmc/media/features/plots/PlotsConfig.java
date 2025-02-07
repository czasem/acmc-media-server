package pl.acmc.media.features.plots;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

import java.util.ArrayList;
import java.util.List;

public class PlotsConfig extends OkaeriConfig {
    @Comment("Wiadomość pomocy plots")
    List<String> helpMessage = List.of("/cwel {PLAYER}");
}
