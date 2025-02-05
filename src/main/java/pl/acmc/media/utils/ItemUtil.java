package pl.acmc.media.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemUtil {

    public static boolean isStackable(ItemStack itemStack) {
        return itemStack.getMaxStackSize() > 1;
    }

    public static boolean isNotStackable(ItemStack itemStack) {
        return itemStack.getMaxStackSize() <= 1;
    }
    public static boolean hasSpace(Player player, ItemStack itemStack, int amount) {
        PlayerInventory inventory = player.getInventory();
        int space = 0;

        int maxStackSize = itemStack.getMaxStackSize();
        boolean isFullStack = (itemStack.getAmount() == maxStackSize);

        for (ItemStack content : inventory.getStorageContents()) {
            if (content == null) {
                space += (maxStackSize == 1) ? 1 : maxStackSize;
            } else if (!isFullStack && content.getType() == itemStack.getType()) {
                if (ItemUtil.isSameItem(content, itemStack)) {
                    int currentAmount = content.getAmount();
                    if (currentAmount < maxStackSize) {
                        space += maxStackSize - currentAmount;
                    }
                }
            }

            if (space >= amount) {
                return true;
            }
        }

        return false;
    }

    public static ItemStack createPotion(String hexColor, String name, List<String> lore, int cmd) {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();

        int red = Integer.parseInt(hexColor.substring(1, 3), 16);
        int green = Integer.parseInt(hexColor.substring(3, 5), 16);
        int blue = Integer.parseInt(hexColor.substring(5, 7), 16);

        Color color = Color.fromRGB(red, green, blue);

        if (meta != null) {
            meta.setColor(color);
            meta.setDisplayName(ChatUtil.coloredHex(name));
            meta.setLore(ChatUtil.colored(lore));
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.setCustomModelData(cmd);
        }

        potion.setItemMeta(meta);
        return potion;
    }

    public static boolean isItemUsed(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta instanceof Damageable) {
            Damageable damageMeta = (Damageable) meta;
            return damageMeta.getDamage() > 0;
        }
        return false;
    }

    public static int calcItem(Player player, ItemStack fromItem)
    {
        int amount = 0;
        for(ItemStack itemStack : player.getInventory())
        {
            if(itemStack==null || itemStack.getType()== Material.AIR)
                continue;

            if(itemStack.isSimilar(fromItem))
                amount+=itemStack.getAmount();
        }

        return amount;
    }

    public static void removeItem(Player player, ItemStack fromItem, int amount)
    {
        int removeItem = amount;


        for(ItemStack itemStack : player.getInventory()) {
            if(itemStack==null || itemStack.getType()== Material.AIR)
                continue;

            if(itemStack.isSimilar(fromItem))
            {
                if(removeItem<=0)
                    break;

                if(itemStack.getAmount()<=removeItem)
                {
                    removeItem-=itemStack.getAmount();
                    itemStack.setAmount(0);
                } else {
                    itemStack.setAmount(itemStack.getAmount()-removeItem);
                    removeItem=0;
                }
            }
        }
    }

    public static boolean isSameName(ItemStack itemStack, ItemStack secondItem) {
        boolean firstCheck = itemStack.getType().equals(secondItem.getType());
        if (!firstCheck)
            return false;
        ItemMeta itemMeta = itemStack.getItemMeta();
        ItemMeta sItemMeta = secondItem.getItemMeta();
        if (itemMeta == null && sItemMeta == null)
            return true;
        if (itemMeta == null || sItemMeta == null)
            return false;
        if (itemMeta.hasDisplayName() && sItemMeta.hasDisplayName()) {
            return itemMeta.getDisplayName().equals(sItemMeta.getDisplayName());
        } else return !itemMeta.hasDisplayName() && !sItemMeta.hasDisplayName();
    }
    public static boolean isSameItem(ItemStack itemStack, ItemStack secondItem) {
        boolean firstCheck = itemStack.getType().equals(secondItem.getType());
        if (!firstCheck)
            return false;
        ItemMeta itemMeta = itemStack.getItemMeta();
        ItemMeta sItemMeta = secondItem.getItemMeta();
        if (itemMeta == null && sItemMeta == null)
            return true;
        if (itemMeta == null || sItemMeta == null)
            return false;
        if (itemMeta.hasDisplayName() && sItemMeta.hasDisplayName()) {
            if (!itemMeta.getDisplayName().equals(sItemMeta.getDisplayName()))
                return false;
        } else if (itemMeta.hasDisplayName() || sItemMeta.hasDisplayName()) {
            return false;
        }
        if (itemMeta.hasLore() && sItemMeta.hasLore()) {
            if (!itemMeta.getLore().equals(sItemMeta.getLore()))
                return false;
        } else if (itemMeta.hasLore() || sItemMeta.hasLore()) {
            return false;
        }
        if (itemMeta.hasEnchants() && sItemMeta.hasEnchants()) {
            if (!itemMeta.getEnchants().equals(sItemMeta.getEnchants()))
                return false;
        } else if (itemMeta.hasEnchants() || sItemMeta.hasEnchants()) {
            return false;
        }
        return true;
    }

    public static void giveItems(Player player, List<ItemStack> items, JavaPlugin plugin) {
        ItemStack[] stockArr = new ItemStack[items.size()];
        HashMap<Integer, ItemStack> notStored = player.getInventory().addItem(items.<ItemStack>toArray(stockArr));
        if (notStored.isEmpty())
            return;
        Bukkit.getScheduler().runTask(plugin, () -> {
            for (Map.Entry<Integer, ItemStack> en : (Iterable<Map.Entry<Integer, ItemStack>>)notStored.entrySet()) {
                if (en.getValue() == null || ((ItemStack)en.getValue()).getType() == Material.AIR)
                    continue;
                player.getWorld().dropItemNaturally(player.getLocation(), en.getValue());
            }
        });
    }

    public static void giveItem(Player player, ItemStack item, JavaPlugin plugin) {
        giveItems(player, Collections.singletonList(item), plugin);
    }

    public static ItemStack applyPlaceholders(Player player, ItemStack item, Map<String, String> placeholders) {
        if (item == null || placeholders == null || placeholders.isEmpty()) {
            return item;
        }

        ItemStack newItem = new ItemStack(item);
        ItemMeta meta = newItem.getItemMeta();

        if (meta == null) {
            return newItem;
        }

        if (meta.hasDisplayName()) {
            String displayName = meta.getDisplayName();
            displayName = applyPlaceholdersToString(player, displayName, placeholders);
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        }

        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                line = applyPlaceholdersToString(player, line, placeholders);
                lore.set(i, ChatColor.translateAlternateColorCodes('&', line));
            }
            meta.setLore(lore);
        }

        newItem.setItemMeta(meta);
        return newItem;
    }

    public static ItemStack applyPlaceholders(Player player,ItemStack item, Object... placeholders) {
        if (placeholders.length % 2 != 0) {
            throw new IllegalArgumentException("Placeholders must be in the format: key, value.");
        }

        Map<String, String> placeholderMap = new HashMap<>();
        for (int i = 0; i < placeholders.length; i += 2) {
            String key = placeholders[i].toString();
            String value = placeholders[i + 1].toString();
            placeholderMap.put(key, value);
        }

        return applyPlaceholders(player, item, placeholderMap);
    }

    private static String applyPlaceholdersToString(Player player, String input, Map<String, String> placeholders) {
        if (input == null || placeholders == null || placeholders.isEmpty()) {
            return input;
        }

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            input = input.replace(placeholder, entry.getValue());
        }

        input = PlaceholderAPI.setPlaceholders(player, input);

        return input;
    }

    public static ItemStack[] getShulkerContents(ItemStack shulker) {
        if (shulker == null || !shulker.getType().name().endsWith("SHULKER_BOX")) return new ItemStack[27];
        var meta = (BlockStateMeta) shulker.getItemMeta();
        if (meta == null || !(meta.getBlockState() instanceof ShulkerBox)) return new ItemStack[27];
        return ((ShulkerBox) meta.getBlockState()).getInventory().getContents();
    }

    public static void setShulkerContents(ItemStack shulker, ItemStack[] contents) {
        if (shulker == null || !shulker.getType().name().endsWith("SHULKER_BOX")) return;
        var meta = (BlockStateMeta) shulker.getItemMeta();
        if (meta == null || !(meta.getBlockState() instanceof ShulkerBox)) return;
        ShulkerBox box = (ShulkerBox) meta.getBlockState();
        box.getInventory().setContents(contents);
        meta.setBlockState(box);
        shulker.setItemMeta(meta);
    }

}
