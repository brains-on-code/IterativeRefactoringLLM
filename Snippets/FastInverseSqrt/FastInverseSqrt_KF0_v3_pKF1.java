package com.thealgorithms.maths;

/**
 * Utility class for computing and validating the fast inverse square root.
 * <a href="https://en.wikipedia.org/wiki/Fast_inverse_square_root">Wikipedia</a>
 */
public final class FastInverseSqrt {

    private FastInverseSqrt() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns whether the fast inverse square root (float) matches the exact value
     * 1 / sqrt(value) up to typical float precision (6–8 decimal places).
     */
    public static boolean inverseSqrt(float value) {
        float halfValue = 0.5f * value;

        int bits = Float.floatToIntBits(value);
        bits = 0x5f3759df - (bits >> 1);
        float estimate = Float.intBitsToFloat(bits);

        estimate = estimate * (1.5f - halfValue * estimate * estimate);

        float exact = 1.0f / (float) Math.sqrt(value);
        return estimate == exact;
    }

    /**
     * Returns whether the fast inverse square root (double) matches the exact value
     * 1 / sqrt(value) up to typical double precision (14–16 decimal places).
     */
    public static boolean inverseSqrt(double value) {
        double halfValue = 0.5d * value;

        long bits = Double.doubleToLongBits(value);
        bits = 0x5fe6ec85e7de30daL - (bits >> 1);
        double estimate = Double.longBitsToDouble(bits);

        for (int i = 0; i < 4; i++) {
            estimate = estimate * (1.5d - halfValue * estimate * estimate);
        }

        double exact = 1.0d / Math.sqrt(value);
        return estimate == exact;
    }
}