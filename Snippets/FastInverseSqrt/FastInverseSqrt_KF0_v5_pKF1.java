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
        float halfOfValue = 0.5f * value;

        int valueAsBits = Float.floatToIntBits(value);
        int adjustedBits = 0x5f3759df - (valueAsBits >> 1);
        float inverseSqrtApproximation = Float.intBitsToFloat(adjustedBits);

        inverseSqrtApproximation =
                inverseSqrtApproximation
                        * (1.5f - halfOfValue * inverseSqrtApproximation * inverseSqrtApproximation);

        float exactInverseSqrt = 1.0f / (float) Math.sqrt(value);
        return inverseSqrtApproximation == exactInverseSqrt;
    }

    /**
     * Returns whether the fast inverse square root (double) matches the exact value
     * 1 / sqrt(value) up to typical double precision (14–16 decimal places).
     */
    public static boolean inverseSqrt(double value) {
        double halfOfValue = 0.5d * value;

        long valueAsBits = Double.doubleToLongBits(value);
        long adjustedBits = 0x5fe6ec85e7de30daL - (valueAsBits >> 1);
        double inverseSqrtApproximation = Double.longBitsToDouble(adjustedBits);

        for (int iterationIndex = 0; iterationIndex < 4; iterationIndex++) {
            inverseSqrtApproximation =
                    inverseSqrtApproximation
                            * (1.5d - halfOfValue * inverseSqrtApproximation * inverseSqrtApproximation);
        }

        double exactInverseSqrt = 1.0d / Math.sqrt(value);
        return inverseSqrtApproximation == exactInverseSqrt;
    }
}