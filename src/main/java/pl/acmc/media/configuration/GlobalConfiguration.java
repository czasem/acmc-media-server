package pl.acmc.media.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import pl.acmc.media.api.messager.type.MessagerType;

import java.util.ArrayList;
import java.util.List;

public class GlobalConfiguration extends OkaeriConfig {
    public GlobalConfiguration() {
        kickReason.add("&c&lZOSTAŁEŚ WYRZUCONY");
        kickReason.add("&7");
        kickReason.add("&7Zostałeś wyrzucony z tego trybu.");

        maxSlotsReason.add("&cSerwer jest pełny.");

        joinKickMessage.add("&cNie możesz dołączyć na ten tryb.");
    }

    @Comment("Powód wyrzucenia gracza z trybu")
    public List<String> kickReason = new ArrayList<>();


    @Comment("Maksymalna ilość slotów")
    public Integer maxSlots = 100;

    @Comment("Powód wyrzucenia gracz w przypadku maksymalnej ilości slotów")
    public List<String> maxSlotsReason = new ArrayList<>();

    @Comment("Możliwość dołączenia na serwer bez uprawnień (Jako gracz)")
    public Boolean join = false;

    @Comment("Powód wyrzucenia w przypadku blokady dołączenia")
    public List<String> joinKickMessage = new ArrayList<>();



}
