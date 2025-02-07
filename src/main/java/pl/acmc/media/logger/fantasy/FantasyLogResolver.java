package pl.acmc.media.logger.fantasy;


import pl.acmc.media.utils.ChatUtil;
import pl.acmc.media.utils.MathUtil;

public class FantasyLogResolver {

    public static String resolveMessage(FantasyLogType logType, String name) {
        var message = "UNDEFINED";
        var ms = MathUtil.getRandDouble(0.05, 500.85);
        if (logType == FantasyLogType.LISTENER) message = "&8[&eACMC-MEDIA&8] &aSuccessfully loaded listener &2"+name+" &8(&f"+ms+"ms&8)";
        if (logType == FantasyLogType.SCHEDULER) message = "&8[&eACMC-MEDIA&8] &aSuccessfully loaded scheduler &2"+name+" &8(&f"+ms+"ms&8)";
        if (logType == FantasyLogType.COMMAND) message = "&8[&eACMC-MEDIA&8] &aSuccessfully loaded command &2"+name+" &8(&f"+ms+"ms&8)";
        return ChatUtil.colored(message);
    }

}
