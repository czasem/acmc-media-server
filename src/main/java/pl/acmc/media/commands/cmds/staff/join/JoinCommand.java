package pl.acmc.media.commands.cmds.staff.join;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.acmc.media.Main;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;

import static pl.acmc.media.Main.globalConfiguration;

@Permission("acmc.join.command")
@Command(name = "join")
public class JoinCommand {

    @Execute
    void executeJoin(@Context Player player) {

        Messager.send(player, "Poprawne użycie &4/join [on/off/slots]", MessagerType.ERROR);
    }

    @Execute(name = "on")
    void executeJoinOn(@Context Player player) {

        Boolean joinStatus = globalConfiguration.join;
        if (joinStatus) {
            Messager.send(player, "Możliwość dołączenia na serwer jest już &fwłączona&c.", MessagerType.ERROR);
            return;
        }
        globalConfiguration.join = true;
        globalConfiguration.save();
        Messager.send(player, "Możliwość dołączenia na serwer została &fwłączona&a.", MessagerType.SUCCESS);
    }

    @Execute(name = "off")
    void executeJoinOff(@Context Player player) {

        Boolean joinStatus = globalConfiguration.join;
        if (!joinStatus) {
            Messager.send(player, "Możliwość dołączenia na serwer jest już &fwyłączona&c.", MessagerType.ERROR);
            return;
        }
        globalConfiguration.join = false;
        globalConfiguration.save();
        Messager.send(player, "Możliwość dołączenia na serwer została &fwyłączona&a.", MessagerType.SUCCESS);
    }

    @Execute(name = "slots")
    void executeJoinSlots(@Context Player player, @Arg("sloty") Integer slots) {

        if(slots < 1 || slots > 999) {
            Messager.send(player, "Ilość slotów nie może być mniejsza niż &f1&c oraz większa niż &f999&c.", MessagerType.ERROR);
            return;
        }

        globalConfiguration.maxSlots = slots;
        globalConfiguration.save();
        Messager.send(player, "Ustawiono ilość slotów na &f" + slots + "&a.", MessagerType.SUCCESS);
    }


}
