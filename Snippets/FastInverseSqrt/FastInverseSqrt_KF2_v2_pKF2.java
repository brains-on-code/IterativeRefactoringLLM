package com.thealgorithms.maths;

public final class FastInverseSqrt {

    private FastInverseSqrt() {
        // Prevent instantiation of utility class
    }

    /**
     * Approximates the inverse square root (1 / sqrt(number)) for a float
     * using the fast inverse square root algorithm, then compares it to the
     * exact value.
     *
     * @param number the input value
     * @return true if the approximation equals the exact value, false otherwise
     */
    public static boolean inverseSqrt(float number) {
        float x = number;
        float halfX = 0.5f * x;

        int bits = Float.floatToIntBits(x);
        bits = 0x5f3759df - (bits >> 1); // Magic constant for initial guess
        x = Float.intBitsToFloat(bits);

        // One Newton-Raphson refinement step
        x = x * (1.5f - halfX * x * x);

        float exact = 1.0f / (float) Math.sqrt(number);
        return x == exact;
    }

    /**
     * Approximates the inverse square root (1 / sqrt(number)) for a double
     * using the fast inverse square root algorithm, then compares it to the
     * exact value.
     *
     * @param number the input value
     * @return true if the approximation equals the exact value, false otherwise
     */
    public static boolean inverseSqrt(double number) {
        double x = number;
        double halfX = 0.5d * x;

        long bits = Double.doubleToLongBits(x);
        bits = 0x5fe6ec85e7de30daL - (bits >> 1); // Magic constant for initial guess
        x = Double.longBitsToDouble(bits);

        // Newton-Raphson refinement steps
        for (int i = 0; i < 4; i++) {
            x = x * (1.5d - halfX * x * x);
        }

        // Adjust to compare with 1 / sqrt(number)
        x *= number;

        double exact = 1.0d / Math.sqrt(number);
        return x == exact;
    }
}