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

        int valueBits = Float.floatToIntBits(value);
        int magicAdjustedBits = 0x5f3759df - (valueBits >> 1);
        float inverseSqrtEstimate = Float.intBitsToFloat(magicAdjustedBits);

        inverseSqrtEstimate =
                inverseSqrtEstimate * (1.5f - halfValue * inverseSqrtEstimate * inverseSqrtEstimate);

        float exactInverseSqrt = 1.0f / (float) Math.sqrt(value);
        return inverseSqrtEstimate == exactInverseSqrt;
    }

    /**
     * Returns whether the fast inverse square root (double) matches the exact value
     * 1 / sqrt(value) up to typical double precision (14–16 decimal places).
     */
    public static boolean inverseSqrt(double value) {
        double halfValue = 0.5d * value;

        long valueBits = Double.doubleToLongBits(value);
        long magicAdjustedBits = 0x5fe6ec85e7de30daL - (valueBits >> 1);
        double inverseSqrtEstimate = Double.longBitsToDouble(magicAdjustedBits);

        for (int iteration = 0; iteration < 4; iteration++) {
            inverseSqrtEstimate =
                    inverseSqrtEstimate * (1.5d - halfValue * inverseSqrtEstimate * inverseSqrtEstimate);
        }

        double exactInverseSqrt = 1.0d / Math.sqrt(value);
        return inverseSqrtEstimate == exactInverseSqrt;
    }
}