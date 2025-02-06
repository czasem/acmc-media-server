package pl.acmc.media.utils.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;
import pl.acmc.media.utils.ChatUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BossBarManager {

    private static final Map<String, Map<Player, BossBar>> bossBars = new ConcurrentHashMap<>();
    private static final Map<String, Integer> bossBarTimers = new HashMap<>();

    public static BossBarBuilder create(String name) {
        return new BossBarBuilder(name);
    }

    public static void remove(String name, Player player) {
        Map<Player, BossBar> playerBossBars = bossBars.get(name);
        if (playerBossBars != null) {
            BossBar bossBar = playerBossBars.get(player);
            if (bossBar != null) {
                bossBar.removePlayer(player);
            }
            playerBossBars.remove(player);

            if (playerBossBars.isEmpty()) {
                bossBars.remove(name);
                Integer timerId = bossBarTimers.remove(name);
                if (timerId != null) {
                    Bukkit.getScheduler().cancelTask(timerId);
                }
            }
        }
    }


    public static void change(String name, Player player, String newTitle, double newProgress) {
        Map<Player, BossBar> playerBossBars = bossBars.get(name);
        if (playerBossBars == null) {
            throw new IllegalArgumentException("No BossBar found with name: " + name);
        }

        BossBar bossBar = playerBossBars.get(player);
        if (bossBar == null) {
            throw new IllegalArgumentException("No BossBar found for player: " + player.getName());
        }

        bossBar.setTitle(ChatUtil.coloredHex(newTitle));
        bossBar.setProgress(newProgress / 100.0);
    }

    public static class BossBarBuilder {
        private final String name;
        private String title;
        private double progress;
        private int time;
        private Player player;
        private BarColor color = BarColor.GREEN;
        private BarStyle style = BarStyle.SOLID;

        private BossBarBuilder(String name) {
            this.name = name;
        }

        public BossBarBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BossBarBuilder progress(double progress) {
            this.progress = progress;
            return this;
        }

        public BossBarBuilder time(int time) {
            this.time = time;
            return this;
        }

        public BossBarBuilder player(Player player) {
            this.player = player;
            return this;
        }

        public BossBarBuilder color(BarColor color) {
            this.color = color;
            return this;
        }

        public BossBarBuilder style(BarStyle style) {
            this.style = style;
            return this;
        }

        public void build() {
            if (player == null) {
                throw new IllegalArgumentException("Player must be specified");
            }

            BossBar bossBar = Bukkit.createBossBar(ChatUtil.coloredHex(title), color, style);
            bossBar.setProgress(progress / 100.0);
            bossBar.addPlayer(player);

            bossBars.computeIfAbsent(name, k -> new HashMap<>()).put(player, bossBar);

            if (time > 0) {
                int taskId = new BukkitRunnable() {
                    private double currentProgress = progress;
                    private int ticksPassed = 0;

                    @Override
                    public void run() {
                        ticksPassed++;

                        if (ticksPassed >= (time * 20)) {
                            remove(name, player);
                            cancel();
                            return;
                        }

                        currentProgress = progress * (1 - ((double) ticksPassed / (time * 20)));
                        bossBar.setProgress(currentProgress / 100.0);

                        if (currentProgress <= 0) {
                            remove(name, player);
                            cancel();
                        }
                    }
                }.runTaskTimer(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), 0L, 1L).getTaskId();
                bossBarTimers.put(name, taskId);
            }
        }

    }

    private static class BossBarUpdater extends BukkitRunnable {
        private final String name;
        private final Player player;
        private final String newTitle;
        private final double newProgress;

        public BossBarUpdater(String name, Player player, String newTitle, double newProgress) {
            this.name = name;
            this.player = player;
            this.newTitle = newTitle;
            this.newProgress = newProgress;
        }

        @Override
        public void run() {
            Map<Player, BossBar> playerBossBars = bossBars.get(name);
            if (playerBossBars != null) {
                BossBar bossBar = playerBossBars.get(player);
                if (bossBar != null) {
                    bossBar.setTitle(ChatUtil.coloredHex(newTitle));
                    bossBar.setProgress(newProgress / 100.0);
                    if (bossBar.getProgress() <= 0) {
                        remove(name, player);
                        cancel();
                    }
                }
            }
        }
    }
}
