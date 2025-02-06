package pl.acmc.media.commands.cmds.staff.pvp;

import com.comphenix.protocol.events.PacketListener;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;

public class PvPListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {

        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }

        Player attacker = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();


        if(PvPCommand.pvpStatus) {
            return;
        } else {
            if(attacker.hasPermission("acmc.pvp.damage")) {
                return;
            }
            Messager.send(attacker, "PvP jest wyłączone!", MessagerType.ERROR);
            event.setCancelled(true);
        }
    }
}
