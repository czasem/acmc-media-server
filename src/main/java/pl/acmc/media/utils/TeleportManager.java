package pl.acmc.media.utils;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TeleportManager {
    private static final ConcurrentHashMap<UUID, Teleporter> cache = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<UUID, Teleporter> getCache() {
        return cache;
    }

    public static Teleporter get(UUID uniqueId) {
        return cache.get(uniqueId);
    }

    public static void add(Teleporter teleporter) {
        Teleporter existing = cache.get(teleporter.getUniqueId());
        if (existing != null) {
            existing.cancel();
        }
        cache.put(teleporter.getUniqueId(), teleporter);
    }

    public static void remove(UUID uniqueId) {
        Teleporter teleporter = get(uniqueId);
        if (teleporter != null) {
            teleporter.cancel();
            cache.remove(uniqueId);
        }
    }

    public static void cancel(UUID uniqueId) {
        Teleporter teleporter = get(uniqueId);
        if (teleporter != null) {
            teleporter.cancel();
        }
    }

    public static Teleporter teleport(UUID uniqueId) {
        Teleporter existing = get(uniqueId);
        if (existing != null) {
            existing.cancel();
        }
        Teleporter teleporter = new Teleporter(uniqueId);
        add(teleporter);
        return teleporter;
    }
}
