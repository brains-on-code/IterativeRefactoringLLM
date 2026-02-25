package com.thealgorithms.maths;

public final class FastInverseSqrt {

    private static final int FLOAT_MAGIC = 0x5f3759df;
    private static final long DOUBLE_MAGIC = 0x5fe6ec85e7de30daL;

    private FastInverseSqrt() {
        // Prevent instantiation
    }

    /**
     * Uses the fast inverse square root algorithm to approximate 1 / sqrt(number)
     * for a float, then compares it to the exact value.
     *
     * @param number the input value
     * @return true if the approximation equals the exact value, false otherwise
     */
    public static boolean inverseSqrt(float number) {
        float x = number;
        float halfX = 0.5f * x;

        int bits = Float.floatToIntBits(x);
        bits = FLOAT_MAGIC - (bits >> 1); // Initial approximation
        x = Float.intBitsToFloat(bits);

        x = x * (1.5f - halfX * x * x); // One Newton-Raphson iteration

        float exact = 1.0f / (float) Math.sqrt(number);
        return x == exact;
    }

    /**
     * Uses the fast inverse square root algorithm to approximate 1 / sqrt(number)
     * for a double, then compares it to the exact value.
     *
     * @param number the input value
     * @return true if the approximation equals the exact value, false otherwise
     */
    public static boolean inverseSqrt(double number) {
        double x = number;
        double halfX = 0.5d * x;

        long bits = Double.doubleToLongBits(x);
        bits = DOUBLE_MAGIC - (bits >> 1); // Initial approximation
        x = Double.longBitsToDouble(bits);

        // Newton-Raphson refinement
        for (int i = 0; i < 4; i++) {
            x = x * (1.5d - halfX * x * x);
        }

        x *= number; // Adjust to compare with 1 / sqrt(number)

        double exact = 1.0d / Math.sqrt(number);
        return x == exact;
    }
}