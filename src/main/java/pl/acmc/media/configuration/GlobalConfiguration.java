package pl.acmc.media.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import pl.acmc.media.api.messager.type.MessagerType;

import java.util.ArrayList;
import java.util.List;

public class GlobalConfiguration extends OkaeriConfig {
    @Comment("Powód wyrzucenia gracza z trybu")
    public List<String> kickReason = List.of("&c&lZOSTAŁEŚ WYRZUCONY", "", "&7Zostałeś wyrzucony z tego trybu.");


    @Comment("Maksymalna ilość slotów")
    public Integer maxSlots = 100;

    @Comment("Powód wyrzucenia gracz w przypadku maksymalnej ilości slotów")
    public List<String> maxSlotsReason = List.of("&cSerwer jest pełny.");

    @Comment("Możliwość dołączenia na serwer bez uprawnień (Jako gracz)")
    public Boolean join = false;

    @Comment("Powód wyrzucenia w przypadku blokady dołączenia")
    public List<String> joinKickMessage = List.of("&cNie możesz dołączyć na ten tryb!");



}
