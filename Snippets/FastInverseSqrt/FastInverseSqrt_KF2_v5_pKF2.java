package com.thealgorithms.maths;

public final class FastInverseSqrt {

    /**
     * Magic constant used for the initial approximation of the inverse square root
     * for 32-bit floating point numbers.
     */
    private static final int FLOAT_MAGIC = 0x5f3759df;

    /**
     * Magic constant used for the initial approximation of the inverse square root
     * for 64-bit floating point numbers.
     */
    private static final long DOUBLE_MAGIC = 0x5fe6ec85e7de30daL;

    private FastInverseSqrt() {
        // Utility class; prevent instantiation.
    }

    /**
     * Approximates 1 / sqrt(number) for a float using the fast inverse square root
     * algorithm and compares it to the exact value.
     *
     * @param number the input value
     * @return true if the approximation equals the exact value, false otherwise
     */
    public static boolean inverseSqrt(float number) {
        float x = number;
        float halfX = 0.5f * x;

        // Interpret the float bits as an integer, apply the magic constant to get
        // an initial approximation, then reinterpret back as a float.
        int bits = Float.floatToIntBits(x);
        bits = FLOAT_MAGIC - (bits >> 1);
        x = Float.intBitsToFloat(bits);

        // Perform one Newton-Raphson iteration to refine the approximation.
        x = x * (1.5f - halfX * x * x);

        float exact = 1.0f / (float) Math.sqrt(number);
        return x == exact;
    }

    /**
     * Approximates 1 / sqrt(number) for a double using the fast inverse square root
     * algorithm and compares it to the exact value.
     *
     * @param number the input value
     * @return true if the approximation equals the exact value, false otherwise
     */
    public static boolean inverseSqrt(double number) {
        double x = number;
        double halfX = 0.5d * x;

        // Interpret the double bits as a long, apply the magic constant to get
        // an initial approximation, then reinterpret back as a double.
        long bits = Double.doubleToLongBits(x);
        bits = DOUBLE_MAGIC - (bits >> 1);
        x = Double.longBitsToDouble(bits);

        // Perform several Newton-Raphson iterations to refine the approximation.
        for (int i = 0; i < 4; i++) {
            x = x * (1.5d - halfX * x * x);
        }

        // Adjust the approximation to compare with 1 / sqrt(number).
        x *= number;

        double exact = 1.0d / Math.sqrt(number);
        return x == exact;
    }
}