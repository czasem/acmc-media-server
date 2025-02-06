package pl.acmc.media.features.spawn;

import dev.rollczi.litecommands.annotations.command.Command;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import pl.acmc.media.api.messager.type.MessagerType;
import pl.acmc.media.utils.location.SerializableLocation;

public class SpawnConfig extends OkaeriConfig {
    @Comment("Lokalizacja spawnu")
    SerializableLocation spawnLocation;
    @Comment("Wiadomość po ustawieniu lokalizacji spawnu")
    String messageWhenLocalizationSetted = "Pomyślnie ustawiono lokalizacje spawnu";
    @Comment({"Typ wysyłania wiadomości", "Dostępne typy:", " SUCCESS, ERROR, MESSAGE, ACTIONBAR, SUBTITLE, CHAT"})
    MessagerType messageWhenLocalizationSettedMessagerType = MessagerType.SUCCESS;
}
