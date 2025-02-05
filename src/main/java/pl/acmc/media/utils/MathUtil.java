package pl.acmc.media.utils;

import java.util.concurrent.ThreadLocalRandom;

public class MathUtil {

    public static int getRandInt(int min, int max) {
        if (min == max) {
            return min;
        }
        if (max <= min) {
            throw new IllegalArgumentException("Max can't be smaller than min!");
        }
        return ThreadLocalRandom.current().nextInt(max - min + 1) + min;
    }

    public static double getRandDouble(double min, double max) {
        if (min == max) {
            return min;
        }
        if (max <= min) {
            throw new IllegalArgumentException("Max can't be smaller than min!");
        }
        return ThreadLocalRandom.current().nextDouble() * (max - min) + min;
    }

    public static float getRandFloat(float min, float max) {
        if (min == max) {
            return min;
        }
        if (max <= min) {
            throw new IllegalArgumentException("Max can't be smaller than min!");
        }
        return ThreadLocalRandom.current().nextFloat() * (max - min) + min;
    }

    public static boolean getChance(double chance) {
        return chance >= 100.0 || chance >= getRandDouble(0.0, 100.0);
    }
}
