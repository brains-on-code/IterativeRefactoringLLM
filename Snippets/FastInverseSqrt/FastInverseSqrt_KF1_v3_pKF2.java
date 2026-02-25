package com.thealgorithms.maths;

/**
 * Utility class for fast inverse square root related methods.
 */
public final class FastInverseSquareRoot {

    private static final int FLOAT_MAGIC = 0x5f3759df;
    private static final long DOUBLE_MAGIC = 0x5fe6ec85e7de30daL;

    private FastInverseSquareRoot() {
        // Prevent instantiation
    }

    /**
     * Returns whether the fast inverse square root approximation for a float
     * (with one Newton–Raphson iteration) exactly matches 1 / sqrt(value).
     *
     * Uses the classic 0x5f3759df magic constant.
     *
     * @param value input value
     * @return true if the approximation equals 1 / sqrt(value), false otherwise
     */
    public static boolean matchesExactInverseSqrt(float value) {
        float x = value;
        float halfX = 0.5f * x;

        int bits = Float.floatToIntBits(x);
        bits = FLOAT_MAGIC - (bits >> 1);
        x = Float.intBitsToFloat(bits);

        x = x * (1.5f - halfX * x * x);

        return x == (1.0f / (float) Math.sqrt(value));
    }

    /**
     * Returns whether the fast inverse square root approximation for a double
     * (with multiple Newton–Raphson iterations) exactly matches 1 / sqrt(value).
     *
     * Uses the 0x5fe6ec85e7de30daL magic constant.
     *
     * @param value input value
     * @return true if the approximation equals 1 / sqrt(value), false otherwise
     */
    public static boolean matchesExactInverseSqrt(double value) {
        double x = value;
        double halfX = 0.5d * x;

        long bits = Double.doubleToLongBits(x);
        bits = DOUBLE_MAGIC - (bits >> 1);
        x = Double.longBitsToDouble(bits);

        for (int i = 0; i < 4; i++) {
            x = x * (1.5d - halfX * x * x);
        }

        x *= value;

        return x == 1.0d / Math.sqrt(value);
    }
}