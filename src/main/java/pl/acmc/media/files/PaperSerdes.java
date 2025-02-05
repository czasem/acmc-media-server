package pl.acmc.media.files;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.configs.yaml.bukkit.serdes.serializer.ItemStackSerializer;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class PaperSerdes implements OkaeriSerdesPack {

    @Override
    public void register(@NonNull SerdesRegistry registry) {

        registry.register(new SerdesBukkit());

        registry.registerExclusive(ItemStack.class, new ItemStackSerializer(true));
    }
}