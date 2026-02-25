package com.thealgorithms.maths;

public final class FastInverseSqrt {

    private FastInverseSqrt() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes an approximation of the inverse square root (1 / sqrt(number))
     * for a float using the fast inverse square root algorithm and checks it
     * against the exact value.
     *
     * @param number the input value
     * @return true if the approximation equals the exact value, false otherwise
     */
    public static boolean inverseSqrt(float number) {
        float x = number;
        float halfX = 0.5f * x;

        // Interpret the float bits as an int
        int bits = Float.floatToIntBits(x);

        // Initial guess using the magic constant
        bits = 0x5f3759df - (bits >> 1);

        // Convert bits back to float
        x = Float.intBitsToFloat(bits);

        // One iteration of Newton-Raphson refinement
        x = x * (1.5f - halfX * x * x);

        float exact = 1.0f / (float) Math.sqrt(number);
        return x == exact;
    }

    /**
     * Computes an approximation of the inverse square root (1 / sqrt(number))
     * for a double using the fast inverse square root algorithm and checks it
     * against the exact value.
     *
     * @param number the input value
     * @return true if the approximation equals the exact value, false otherwise
     */
    public static boolean inverseSqrt(double number) {
        double x = number;
        double halfX = 0.5d * x;

        // Interpret the double bits as a long
        long bits = Double.doubleToLongBits(x);

        // Initial guess using the magic constant
        bits = 0x5fe6ec85e7de30daL - (bits >> 1);

        // Convert bits back to double
        x = Double.longBitsToDouble(bits);

        // Multiple Newton-Raphson refinement iterations
        for (int iteration = 0; iteration < 4; iteration++) {
            x = x * (1.5d - halfX * x * x);
        }

        // Adjust to compare with 1 / sqrt(number)
        x *= number;

        double exact = 1.0d / Math.sqrt(number);
        return x == exact;
    }
}