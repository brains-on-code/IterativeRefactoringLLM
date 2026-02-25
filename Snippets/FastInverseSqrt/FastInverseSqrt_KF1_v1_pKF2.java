package com.thealgorithms.maths;

/**
 * Utility class for fast inverse square root related methods.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Verifies the fast inverse square root approximation for a float input
     * using a single Newton-Raphson iteration.
     *
     * This uses the classic "0x5f3759df" magic constant.
     *
     * @param value input value
     * @return true if the approximation equals 1 / sqrt(value), false otherwise
     */
    public static boolean method2(float value) {
        float x = value;
        float halfX = 0.5f * x;

        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1); // initial guess
        x = Float.intBitsToFloat(i);

        // One iteration of Newton-Raphson refinement
        x = x * (1.5f - halfX * x * x);

        return x == (1.0f / (float) Math.sqrt(value));
    }

    /**
     * Verifies the fast inverse square root approximation for a double input
     * using multiple Newton-Raphson iterations.
     *
     * This uses the "0x5fe6ec85e7de30daL" magic constant.
     *
     * @param value input value
     * @return true if the approximation equals 1 / sqrt(value), false otherwise
     */
    public static boolean method2(double value) {
        double x = value;
        double halfX = 0.5d * x;

        long i = Double.doubleToLongBits(x);
        i = 0x5fe6ec85e7de30daL - (i >> 1); // initial guess
        x = Double.longBitsToDouble(i);

        // Multiple iterations of Newton-Raphson refinement
        for (int iter = 0; iter < 4; iter++) {
            x = x * (1.5d - halfX * x * x);
        }

        x *= value;

        return x == 1.0d / Math.sqrt(value);
    }
}