package com.thealgorithms.maths;

/**
 * Utility class for operations related to the fast inverse square root.
 */
public final class FastInverseSquareRoot {

    /** Magic constant used in the float fast inverse square root approximation. */
    private static final int FLOAT_MAGIC = 0x5f3759df;

    /** Magic constant used in the double fast inverse square root approximation. */
    private static final long DOUBLE_MAGIC = 0x5fe6ec85e7de30daL;

    private FastInverseSquareRoot() {
        // Utility class; prevent instantiation.
    }

    /**
     * Checks whether the fast inverse square root approximation for a float
     * (with one Newton–Raphson iteration) exactly matches {@code 1 / sqrt(value)}.
     *
     * @param value the input value
     * @return {@code true} if the approximation equals {@code 1 / sqrt(value)},
     *         {@code false} otherwise
     */
    public static boolean matchesExactInverseSqrt(float value) {
        float x = value;
        float halfX = 0.5f * x;

        int bits = Float.floatToIntBits(x);
        bits = FLOAT_MAGIC - (bits >> 1);
        x = Float.intBitsToFloat(bits);

        // One Newton–Raphson iteration to refine the approximation.
        x = x * (1.5f - halfX * x * x);

        return x == (1.0f / (float) Math.sqrt(value));
    }

    /**
     * Checks whether the fast inverse square root approximation for a double
     * (with multiple Newton–Raphson iterations) exactly matches {@code 1 / sqrt(value)}.
     *
     * @param value the input value
     * @return {@code true} if the approximation equals {@code 1 / sqrt(value)},
     *         {@code false} otherwise
     */
    public static boolean matchesExactInverseSqrt(double value) {
        double x = value;
        double halfX = 0.5d * x;

        long bits = Double.doubleToLongBits(x);
        bits = DOUBLE_MAGIC - (bits >> 1);
        x = Double.longBitsToDouble(bits);

        // Perform several Newton–Raphson iterations to refine the approximation.
        for (int i = 0; i < 4; i++) {
            x = x * (1.5d - halfX * x * x);
        }

        // Convert from 1/sqrt(value) to sqrt(value) * (1/sqrt(value)) = 1
        x *= value;

        return x == 1.0d / Math.sqrt(value);
    }
}