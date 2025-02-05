package pl.acmc.media.api.builders.others;

import pl.fadecode.mining.animations.DiggingUpdate;
import pl.fadecode.mining.animations.block.BlockViewTask;
import pl.fadecode.mining.utils.Loader;

import java.util.concurrent.TimeUnit;

public class SchedulersBuilder {
    public static void build() {
        Loader.loadScheduler(new BlockViewTask(), 3L, TimeUnit.MILLISECONDS);
        Loader.loadScheduler(new DiggingUpdate(), 10L, TimeUnit.MILLISECONDS);
    }
}
