package com.thealgorithms.maths;

/**
 * Utility class for fast inverse square root related methods.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether the fast inverse square root approximation for a float
     * (with one Newton–Raphson iteration) exactly matches 1 / sqrt(value).
     *
     * Uses the classic 0x5f3759df magic constant.
     *
     * @param value input value
     * @return true if the approximation equals 1 / sqrt(value), false otherwise
     */
    public static boolean method2(float value) {
        float x = value;
        float halfX = 0.5f * x;

        // Initial approximation using the magic constant
        int bits = Float.floatToIntBits(x);
        bits = 0x5f3759df - (bits >> 1);
        x = Float.intBitsToFloat(bits);

        // One Newton–Raphson refinement step
        x = x * (1.5f - halfX * x * x);

        return x == (1.0f / (float) Math.sqrt(value));
    }

    /**
     * Checks whether the fast inverse square root approximation for a double
     * (with multiple Newton–Raphson iterations) exactly matches 1 / sqrt(value).
     *
     * Uses the 0x5fe6ec85e7de30daL magic constant.
     *
     * @param value input value
     * @return true if the approximation equals 1 / sqrt(value), false otherwise
     */
    public static boolean method2(double value) {
        double x = value;
        double halfX = 0.5d * x;

        // Initial approximation using the magic constant
        long bits = Double.doubleToLongBits(x);
        bits = 0x5fe6ec85e7de30daL - (bits >> 1);
        x = Double.longBitsToDouble(bits);

        // Newton–Raphson refinement (4 iterations)
        for (int iter = 0; iter < 4; iter++) {
            x = x * (1.5d - halfX * x * x);
        }

        // Convert inverse square root to (value * inverse square root)
        x *= value;

        return x == 1.0d / Math.sqrt(value);
    }
}