package pl.acmc.media.utils;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class ItemSerializer {

    static {
        ConfigurationSerialization.registerClass(org.bukkit.inventory.meta.SkullMeta.class, "SkullMeta");
    }

    // Serialize an array of ItemStacks to a Base64 string
    public static String itemsToString(ItemStack[] items) {
        try {
            return Base64.getEncoder().encodeToString(itemsToByte(items));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // Deserialize an array of ItemStacks from a Base64 string
    public static ItemStack[] stringToItems(String s) {
        try {
            return byteToItems(Base64.getDecoder().decode(s));
        } catch (Exception e) {
            e.printStackTrace();
            return new ItemStack[]{new ItemStack(Material.AIR)};
        }
    }

    // Serialize a single ItemStack to a Base64 string
    public static String itemToString(ItemStack item) {
        try {
            return Base64.getEncoder().encodeToString(itemToByte(item));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // Deserialize a single ItemStack from a Base64 string
    public static ItemStack stringToItem(String s) {
        try {
            return byteToItem(Base64.getDecoder().decode(s));
        } catch (Exception e) {
            e.printStackTrace();
            return new ItemStack(Material.AIR);
        }
    }

    // Serialize a single ItemStack to a byte array
    public static byte[] itemToByte(ItemStack item) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            BukkitObjectOutputStream oos = new BukkitObjectOutputStream(bos);

            oos.writeObject(item);
            oos.close();
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    // Deserialize a single ItemStack from a byte array
    public static ItemStack byteToItem(byte[] data) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BukkitObjectInputStream ois = new BukkitObjectInputStream(bis);

            ItemStack item = (ItemStack) ois.readObject();
            cleanupItem(item);

            ois.close();
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return new ItemStack(Material.AIR);
        }
    }

    // Serialize an array of ItemStacks to a byte array
    public static byte[] itemsToByte(ItemStack[] items) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            BukkitObjectOutputStream oos = new BukkitObjectOutputStream(bos);

            oos.writeInt(items.length);
            for (ItemStack item : items) {
                oos.writeObject(item);
            }

            oos.close();
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    // Deserialize an array of ItemStacks from a byte array
    public static ItemStack[] byteToItems(byte[] data) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BukkitObjectInputStream ois = new BukkitObjectInputStream(bis);

            int size = ois.readInt();
            ItemStack[] items = new ItemStack[size];
            for (int i = 0; i < size; i++) {
                items[i] = (ItemStack) ois.readObject();
                cleanupItem(items[i]);
            }

            ois.close();
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return new ItemStack[]{new ItemStack(Material.AIR)};
        }
    }

    // Clean up ItemStack metadata to ensure compatibility
    private static void cleanupItem(ItemStack item) {
        if (item != null) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
                if (!meta.hasLore() && dataContainer.getKeys().size() > 0) {
                    dataContainer.getKeys().forEach(key -> dataContainer.remove(key));
                    item.setItemMeta(meta);
                }
            }
        }
    }
}
