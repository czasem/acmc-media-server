package pl.acmc.media.commands.cmds.staff.countdown;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import pl.acmc.media.Main;
import pl.acmc.media.api.messager.Messager;
import pl.acmc.media.api.messager.type.MessagerType;
import pl.acmc.media.scheduler.Scheduler;
import pl.acmc.media.utils.ChatUtil;
import pl.acmc.media.utils.TimeUtil;
import pl.acmc.media.utils.bossbar.BossBarManager;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Permission("acmc.countdown.command")
@Command(name = "countdown", aliases = {"cd", "odliczanie"})
public class CountdownCommand {

    private final Scheduler scheduler = new Scheduler();
    private ScheduledFuture<?> countdownTask;
    @Getter
    private boolean isRunning = false;

    @Execute
    void executeCountdown(@Context Player player) {
        if(player.hasPermission("acmc.countdown.admin")) {
            Messager.send(player, "Poprawne użycie &4/countdown [start/stop] [czas] [komenda]", MessagerType.ERROR);
        }
        if(isRunning) {
            Messager.send(player, "Odliczanie jest włączone", MessagerType.SUCCESS);
            return;
        } else {
            Messager.send(player, "Odliczanie akutalnie jest wyłączone.", MessagerType.ERROR);
        }
    }
    @Permission("acmc.countdown.admin")
    @Execute(name = "start")
    void executeCountdown(@Context Player player, @Arg("czas") String time, @Join("Komenda po zakonczeniu") String command) {
        if(isRunning) {
            Messager.send(player, "Odliczanie jest już w trakcie.", MessagerType.ERROR);
            return;
        }
        long millis = TimeUtil.deserialize(time);
        if (millis <= 0) {
            Messager.send(player, "Nieprawidłowy format czasu.", MessagerType.ERROR);
            return;
        }
        Messager.send(player, "Rozpoczęto odliczanie.", MessagerType.SUCCESS);
        isRunning = true;

        for (Player p : player.getServer().getOnlinePlayers()) {
            BossBarManager.BossBarBuilder bossBarBuilder = BossBarManager.create("countdown")
                    .title(ChatUtil.coloredHex("&7Do końca odliczania pozostało: &f" + TimeUtil.convert(millis)))
                    .progress(100)
                    .player(p)
                    .color(BarColor.PURPLE)
                    .style(BarStyle.SOLID);

            bossBarBuilder.build();
        }

        countdownTask = scheduler.registerScheduleAtFixedRate("countdown", new Runnable() {
            long remainingMillis = millis;

            @Override
            public void run() {
                if (remainingMillis <= 0) {
                    isRunning = false;
                    Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin(Main.getInstance().getDescription().getName()), () -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    });
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        Messager.send(p, "Odliczanie zakończone!", MessagerType.SUCCESS);
                        BossBarManager.remove("countdown", p);
                    }
                    countdownTask.cancel(false);
                    return;
                }

                String color;

                switch ((int) remainingMillis) {
                    case 60000:
                        color = "&e";
                        break;
                    case 30000:
                        color = "&6";
                        break;
                    case 10000:
                    case 9000:
                    case 8000:
                    case 7000:
                    case 6000:
                        color = "&c";
                        break;
                    case 5000:
                    case 4000:
                        color = "&4";
                        break;
                    case 3000:
                    case 2000:
                    case 1000:
                        color = "&4&l";
                        break;
                    default:
                        color = "&f";
                        break;
                }

                switch ((int) remainingMillis) {
                    case 60000 -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            Messager.send(p, "&7Do końca odliczania pozostała " + color + "minuta", MessagerType.CHAT);
                        }
                    }
                    case 30000 -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            Messager.send(p, "&7Do końca odliczania pozostało " + color + "30 sekund", MessagerType.CHAT);
                        }
                    }
                    case 10000 -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            Messager.send(p, "&7Do końca odliczania pozostało " + color + "10 sekund", MessagerType.CHAT);
                        }
                    }
                    case 5000 -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            Messager.send(p, "&7Do końca odliczania pozostało " + color + "5 sekund", MessagerType.CHAT);
                        }
                    }
                    case 3000 -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            Messager.send(p, "&7Do końca odliczania pozostało " + color + "3", MessagerType.CHAT);
                        }
                    }
                    case 2000 -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            Messager.send(p, "&7Do końca odliczania pozostało " + color + "2", MessagerType.CHAT);
                        }
                    }
                    case 1000 -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            Messager.send(p, "&7Do końca odliczania pozostało " + color + "1", MessagerType.CHAT);
                        }
                    }
                }

                double progress = (double) remainingMillis / millis * 100;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    try {
                        BossBarManager.change("countdown", p, ChatUtil.coloredHex("&7Do konca odliczania pozostało: " +color + TimeUtil.convert(remainingMillis)), progress);
                    } catch (IllegalArgumentException e) {
                        countdownTask.cancel(false);
                        return;
                    }
                }

                remainingMillis -= 1000;
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
    @Permission("acmc.countdown.admin")
    @Execute(name = "stop")
    void stopCountdown(@Context Player player) {
        if(!isRunning) {
            Messager.send(player, "Odliczanie nie jest w trakcie.", MessagerType.ERROR);
            return;
        }
        for (Player p : player.getServer().getOnlinePlayers()) {
            BossBarManager.remove("countdown", p);
        }
        if (countdownTask != null) {
            countdownTask.cancel(false);
        }
        Messager.send(player, "Zatrzymano odliczanie.", MessagerType.SUCCESS);
        isRunning = false;
    }
}