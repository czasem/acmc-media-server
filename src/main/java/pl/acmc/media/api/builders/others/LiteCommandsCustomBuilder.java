package pl.acmc.media.api.builders.others;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import dev.rollczi.litecommands.bukkit.LiteBukkitSettings;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.acmc.media.Main;
import pl.acmc.media.commands.argument.PlayerArgumentResolver;
import pl.acmc.media.commands.cmds.players.msg.MsgCommand;
import pl.acmc.media.commands.cmds.players.msg.ReplyCommand;
import pl.acmc.media.commands.cmds.staff.broadcast.BroadcastCommand;
import pl.acmc.media.commands.cmds.staff.countdown.CountdownCommand;
import pl.acmc.media.commands.cmds.staff.kick.KickPlayerCommand;
import pl.acmc.media.commands.cmds.staff.chat.ChatCommand;
import pl.acmc.media.commands.cmds.staff.join.JoinCommand;
import pl.acmc.media.commands.cmds.staff.pvp.PvPCommand;
import pl.acmc.media.commands.cmds.staff.teleport.TpCommand;
import pl.acmc.media.commands.cmds.staff.teleport.TpHereCommand;
import pl.acmc.media.features.plots.PlotsCommand;
import pl.acmc.media.features.spawn.SpawnCommand;
import pl.acmc.media.features.warps.WarpCommand;
import pl.acmc.media.handlers.CommandInvalidUsageHandler;
import pl.acmc.media.handlers.CommandMissingPermissionsHandler;
import pl.acmc.media.logger.GameLogger;
import pl.acmc.media.logger.fantasy.FantasyLogType;
import pl.acmc.media.utils.ChatUtil;
import pl.acmc.media.utils.TimeUtil;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LiteCommandsCustomBuilder {
    public static void build(LiteCommands<CommandSender> liteCommands) {
        LiteCommandsBuilder<CommandSender, LiteBukkitSettings, ?> builder = LiteBukkitFactory.builder("acmc-media", Main.getInstance());

        builder.
                invalidUsage(new CommandInvalidUsageHandler())
                .message(LiteBukkitMessages.PLAYER_ONLY, "&cTylko gracz może użyć tej komendy!")
                .message(LiteBukkitMessages.LOCATION_INVALID_FORMAT, input -> ChatUtil.coloredHex("&#FF0000:( &cNiepoprawny format lokacji: &#FF0000" + input))
                .message(LiteBukkitMessages.COMMAND_COOLDOWN, input -> ChatUtil.coloredHex("&#FF0000:( &cNastępny raz tą komendę będziesz mógł użyć za &#FF0000" + TimeUtil.convert(input.getRemainingDuration().toMillis())))
                .message(LiteBukkitMessages.PLAYER_NOT_FOUND, input -> ChatUtil.coloredHex("&#FF0000:( &cGracz &#FF0000" + input + " &cnie został odnaleziony!"))
                .message(LiteBukkitMessages.WORLD_NOT_EXIST, input -> ChatUtil.coloredHex("&#FF0000:( &cŚwiat &#FF0000" + input + " &cnie został odnaleziony!"))
                .missingPermission(new CommandMissingPermissionsHandler());

        builder
                .argument(Player.class, new PlayerArgumentResolver())
                //.argument(String.class, new WeaponArgument())

                .argumentSuggestion(Integer.class, SuggestionResult.of("1", "2", "3"));

        List<Object> commands = new ArrayList<>();

        commands.addAll(Arrays.asList(
                new KickPlayerCommand(), new WarpCommand(), new SpawnCommand(), new PvPCommand(), new ChatCommand(),
                new JoinCommand(), new MsgCommand(), new ReplyCommand(), new TpHereCommand(), new TpCommand(),
                new CountdownCommand(), new BroadcastCommand(), new PlotsCommand()
        ));

        for (Object object : commands)
            GameLogger.fantasyLog(object, FantasyLogType.COMMAND);

        builder.commands(commands.toArray());

        // build
        liteCommands = builder.build();
    }
}
