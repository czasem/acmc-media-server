package pl.acmc.media.features.warps;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.cooldown.Cooldown;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.shortcut.Shortcut;
import eu.decentsoftware.holograms.api.utils.scheduler.S;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
import pl.acmc.media.Main;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;
import pl.acmc.media.utils.*;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Command(name = "warp", aliases = {"warpy", "warps"})
@Permission("acmc.warp")
public class WarpCommand {

    private final Map<String, WarpProcessor> warps = Main.getWarpConfig().getWarps();

    @Execute
    public void execute(@Context Player player) {
        WarpMenu.open(player);
    }

    @Execute
    @Cooldown(key = "teleport-cooldown", unit = ChronoUnit.SECONDS, count = 1)
    public void teleport(@Context Player player, @Arg("warp") String name) {
        try {
            TeleportManager.teleport(player.getUniqueId())
                    .teleport(LocationUtil.deserialize(warps.get(name).getLocation()));

        } catch (Exception e) {
            Messager.send(player, "error", MessagerType.ERROR);
        }
    }

    @Execute(name = "reload")
    @Permission("acmc.warp.admin")
    public String reload(@Context CommandSender commandSender) {
        Main.getWarpConfig().load();
        return ChatUtil.coloredHex("&APrzeładowano konfigurację warps!");
    }

    @Execute(name = "create")
    @Shortcut("setwarp")
    @Permission("acmc.warp.admin")
    public void create(@Context Player player, @Arg("nazwa") String name, @Arg("slot") int slot, @Join("displayname") String displayname) {
        if (slot > 44 || slot < 0) {
            Messager.send(player, "Niepoprawny slot!", MessagerType.ERROR);
            return;
        }
        if (!displayname.contains("{NAZWA}")) {
            Messager.send(player, "Musisz uzyć placeholderu {NAZWA}, w displayname ktory okresla nazwe warpu w gui!", MessagerType.ERROR);
            return;
        }
        String itemDisplayName = displayname.replace("{NAZWA}", name.toUpperCase(Locale.getDefault()));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&8» &aᴋʟɪᴋɴɪᴊ, ᴀʙʏ ᴘʀᴢᴇᴛᴇʟᴇᴘᴏʀᴛᴏᴡᴀᴄ");
        warps.put(name, new WarpProcessor(
                LocationUtil.serialize(player.getLocation()),
                "warp." + name,
                slot,
                ItemStackBuilder.of(player.getItemInHand())
                        .withName(itemDisplayName)
                        .withLore(lore)
                        .get()
        ));
        Main.getWarpConfig().save();
        Messager.send(player, ChatUtil.coloredHex("&aUstawiłeś warp o nazwie &2" + name + "&r"), MessagerType.SUCCESS);
    }

    @Async
    @Execute(name = "remove")
    @Shortcut("delwarp")
    @Permission("acmc.warp.admin")
    public String remove(@Context Player player, @Arg("warp") String name) {
        warps.remove(name);
        Main.getWarpConfig().save();
        return ChatUtil.coloredHex("&cUsunąłeś warp o nazwie &2" + name + "&r");
    }
}
