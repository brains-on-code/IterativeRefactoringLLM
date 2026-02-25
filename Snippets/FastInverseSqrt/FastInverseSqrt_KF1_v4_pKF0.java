package com.thealgorithms.maths;

/**
 * Utility class for fast inverse square root checks.
 */
public final class FastInverseSqrtChecker {

    private static final int FLOAT_MAGIC = 0x5f3759df;
    private static final long DOUBLE_MAGIC = 0x5fe6ec85e7de30daL;

    private static final float FLOAT_HALF = 0.5f;
    private static final double DOUBLE_HALF = 0.5d;

    private static final float FLOAT_ONE_POINT_FIVE = 1.5f;
    private static final double DOUBLE_ONE_POINT_FIVE = 1.5d;

    private static final float FLOAT_ONE = 1.0f;
    private static final double DOUBLE_ONE = 1.0d;

    private static final int DOUBLE_REFINEMENT_ITERATIONS = 4;

    private FastInverseSqrtChecker() {
        // Utility class; prevent instantiation
    }

    /**
     * Verifies the fast inverse square root approximation for a float input.
     *
     * @param value input value
     * @return true if the approximation equals 1 / sqrt(value), false otherwise
     */
    public static boolean isAccurate(float value) {
        float approximation = fastInverseSqrt(value);
        float exact = FLOAT_ONE / (float) Math.sqrt(value);
        return approximation == exact;
    }

    /**
     * Verifies the fast inverse square root approximation for a double input.
     *
     * @param value input value
     * @return true if the approximation equals 1 / sqrt(value), false otherwise
     */
    public static boolean isAccurate(double value) {
        double approximation = fastInverseSqrt(value);
        double exact = DOUBLE_ONE / Math.sqrt(value);
        return approximation == exact;
    }

    private static float fastInverseSqrt(float value) {
        float halfValue = FLOAT_HALF * value;

        int bits = Float.floatToIntBits(value);
        bits = FLOAT_MAGIC - (bits >> 1);
        float approximation = Float.intBitsToFloat(bits);

        // One iteration of Newton-Raphson refinement
        return approximation * (FLOAT_ONE_POINT_FIVE - halfValue * approximation * approximation);
    }

    private static double fastInverseSqrt(double value) {
        double halfValue = DOUBLE_HALF * value;

        long bits = Double.doubleToLongBits(value);
        bits = DOUBLE_MAGIC - (bits >> 1);
        double approximation = Double.longBitsToDouble(bits);

        // Multiple iterations of Newton-Raphson refinement
        for (int i = 0; i < DOUBLE_REFINEMENT_ITERATIONS; i++) {
            approximation *= (DOUBLE_ONE_POINT_FIVE - halfValue * approximation * approximation);
        }

        return approximation;
    }
}