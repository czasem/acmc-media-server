package pl.acmc.media.commands.argument;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import pl.acmc.media.utils.ChatUtil;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class GameModeArgument extends ArgumentResolver<CommandSender, GameMode> {

    @Override
    public ParseResult<GameMode> parse(Invocation<CommandSender> invocation, Argument<GameMode> context, String argument) {
        GameMode gameMode = GAME_MODE_ARGUMENTS.get(argument.toLowerCase(Locale.getDefault()));
        if (gameMode == null) {
            return ParseResult.failure(ChatUtil.coloredHex("&cPodany tryb gry nie istnieje!"));
        }
        return ParseResult.success(gameMode);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<GameMode> context, SuggestionContext suggestionContext) {
        Set<String> keys = GAME_MODE_ARGUMENTS.keySet();
        return SuggestionResult.of(keys);
    }

    private static final Map<String, GameMode> GAME_MODE_ARGUMENTS = new HashMap<>();

    static {
        for (GameMode value : GameMode.values()) {
            GAME_MODE_ARGUMENTS.put(value.name().toLowerCase(), value);
            GAME_MODE_ARGUMENTS.put(String.valueOf(value.getValue()), value);
        }
    }
}
