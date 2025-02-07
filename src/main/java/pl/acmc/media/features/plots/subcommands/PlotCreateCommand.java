package pl.acmc.media.features.plots.subcommands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "plot")
public class PlotCreateCommand {

    @Execute(name = "stworz")
    public void plotCreate(@Context Player player, @Arg("nazwa")String plotname) {

    }
}
