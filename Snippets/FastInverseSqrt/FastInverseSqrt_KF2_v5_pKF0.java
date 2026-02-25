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
        return Float.floatToIntBits(approximation) == Float.floatToIntBits(exact);
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
        return Double.doubleToLongBits(approximation) == Double.doubleToLongBits(exact);
    }

    /**
     * Fast inverse square root for float.
     *
     * @param number the input value
     * @return approximate value of 1 / sqrt(number)
     */
    private static float fastInverseSqrt(float number) {
        float halfNumber = 0.5f * number;

        int bits = Float.floatToIntBits(number);
        bits = FLOAT_MAGIC - (bits >> 1);
        float approximation = Float.intBitsToFloat(bits);

        return refineFloatApproximation(approximation, halfNumber);
    }

    /**
     * Fast inverse square root for double.
     *
     * @param number the input value
     * @return approximate value of 1 / sqrt(number)
     */
    private static double fastInverseSqrt(double number) {
        double halfNumber = 0.5d * number;

        long bits = Double.doubleToLongBits(number);
        bits = DOUBLE_MAGIC - (bits >> 1);
        double approximation = Double.longBitsToDouble(bits);

        return refineDoubleApproximation(approximation, halfNumber, DOUBLE_REFINEMENT_ITERATIONS);
    }

    private static float refineFloatApproximation(float approximation, float halfNumber) {
        return approximation * (FLOAT_THREE_HALVES - halfNumber * approximation * approximation);
    }

    private static double refineDoubleApproximation(double approximation, double halfNumber, int iterations) {
        double result = approximation;
        for (int i = 0; i < iterations; i++) {
            result = result * (DOUBLE_THREE_HALVES - halfNumber * result * result);
        }
        return result;
    }
}