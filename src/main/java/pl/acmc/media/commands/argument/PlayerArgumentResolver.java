package pl.acmc.media.commands.argument;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.utils.ChatUtil;

import java.util.Set;
import java.util.stream.Collectors;

public class PlayerArgumentResolver extends ArgumentResolver<CommandSender, Player> {

    @Override
    public ParseResult<Player> parse(Invocation<CommandSender> invocation, Argument<Player> context, String argument) {
        Player target = Bukkit.getPlayerExact(argument);
        if (target == null) {
            return ParseResult.failure(ChatUtil.coloredHex("&#FF0029✖ &8| &cGracz o nicku &4" + argument + " &cnie jest online!"));
        }
        return ParseResult.success(target);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Player> context, SuggestionContext suggestionContext) {
        Set<String> playerNames = Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(Collectors.toSet());
        return SuggestionResult.of(playerNames);
    }
}
