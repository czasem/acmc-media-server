package pl.acmc.media.features.warps;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
@Getter
@Setter
public class WarpProcessor extends OkaeriConfig {

    private String location;
    private String permission;
    private int slot;
    private ItemStack itemStack;

    public WarpProcessor(String location, String permission, int slot, ItemStack itemStack) {
        this.location = location;
        this.permission = permission;
        this.slot = slot;
        this.itemStack = itemStack;
    }
}
