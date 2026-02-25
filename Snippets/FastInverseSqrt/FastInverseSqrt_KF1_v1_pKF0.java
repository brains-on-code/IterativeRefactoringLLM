package com.thealgorithms.maths;

/**
 * Utility class for fast inverse square root checks.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Verifies the fast inverse square root approximation for a float input.
     *
     * @param value input value
     * @return true if the approximation equals 1 / sqrt(value), false otherwise
     */
    public static boolean method2(float value) {
        float halfValue = 0.5f * value;

        int bits = Float.floatToIntBits(value);
        bits = 0x5f3759df - (bits >> 1); // Magic number for float
        float approx = Float.intBitsToFloat(bits);

        // One iteration of Newton-Raphson refinement
        approx = approx * (1.5f - halfValue * approx * approx);

        float exact = 1.0f / (float) Math.sqrt(value);
        return approx == exact;
    }

    /**
     * Verifies the fast inverse square root approximation for a double input.
     *
     * @param value input value
     * @return true if the approximation equals 1 / sqrt(value), false otherwise
     */
    public static boolean method2(double value) {
        double halfValue = 0.5d * value;

        long bits = Double.doubleToLongBits(value);
        bits = 0x5fe6ec85e7de30daL - (bits >> 1); // Magic number for double
        double approx = Double.longBitsToDouble(bits);

        // Multiple iterations of Newton-Raphson refinement
        for (int i = 0; i < 4; i++) {
            approx = approx * (1.5d - halfValue * approx * approx);
        }

        approx *= value; // Convert from 1/sqrt(x) to sqrt(x) * 1/sqrt(x) = 1

        double exact = 1.0d / Math.sqrt(value);
        return approx == exact;
    }
}