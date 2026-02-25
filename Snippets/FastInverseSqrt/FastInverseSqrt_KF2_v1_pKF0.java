package com.thealgorithms.maths;

public final class FastInverseSqrt {

    private static final int FLOAT_MAGIC = 0x5f3759df;
    private static final long DOUBLE_MAGIC = 0x5fe6ec85e7de30daL;
    private static final float FLOAT_THREE_HALVES = 1.5f;
    private static final double DOUBLE_THREE_HALVES = 1.5d;
    private static final int DOUBLE_REFINEMENT_ITERATIONS = 4;

    private FastInverseSqrt() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes an approximation of the inverse square root (1 / sqrt(number))
     * using the fast inverse square root algorithm for floats and compares it
     * to the exact value.
     *
     * @param number the input value
     * @return true if the approximation equals the exact value (bitwise), false otherwise
     */
    public static boolean inverseSqrt(float number) {
        float approximation = fastInverseSqrt(number);
        float exact = 1.0f / (float) Math.sqrt(number);
        return approximation == exact;
    }

    /**
     * Computes an approximation of the inverse square root (1 / sqrt(number))
     * using the fast inverse square root algorithm for doubles and compares it
     * to the exact value.
     *
     * @param number the input value
     * @return true if the approximation equals the exact value (bitwise), false otherwise
     */
    public static boolean inverseSqrt(double number) {
        double approximation = fastInverseSqrt(number);
        double exact = 1.0d / Math.sqrt(number);
        return approximation == exact;
    }

    /**
     * Fast inverse square root for float.
     *
     * @param number the input value
     * @return approximate value of 1 / sqrt(number)
     */
    private static float fastInverseSqrt(float number) {
        float x = number;
        float halfX = 0.5f * x;

        int bits = Float.floatToIntBits(x);
        bits = FLOAT_MAGIC - (bits >> 1);
        x = Float.intBitsToFloat(bits);

        // One iteration of Newton-Raphson refinement
        x = x * (FLOAT_THREE_HALVES - halfX * x * x);
        return x;
    }

    /**
     * Fast inverse square root for double.
     *
     * @param number the input value
     * @return approximate value of 1 / sqrt(number)
     */
    private static double fastInverseSqrt(double number) {
        double x = number;
        double halfX = 0.5d * x;

        long bits = Double.doubleToLongBits(x);
        bits = DOUBLE_MAGIC - (bits >> 1);
        x = Double.longBitsToDouble(bits);

        // Multiple iterations of Newton-Raphson refinement for better precision
        for (int iteration = 0; iteration < DOUBLE_REFINEMENT_ITERATIONS; iteration++) {
            x = x * (DOUBLE_THREE_HALVES - halfX * x * x);
        }

        return x;
    }
}