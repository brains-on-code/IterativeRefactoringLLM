package com.thealgorithms.maths;

/**
 * Program to compute the fast inverse square root of a given number.
 *
 * <p>Reference:
 * <a href="https://en.wikipedia.org/wiki/Fast_inverse_square_root">Fast inverse square root</a>
 *
 * <p>This class provides methods that:
 * <ul>
 *   <li>Compute an approximation of 1 / sqrt(x) using the fast inverse square root algorithm.</li>
 *   <li>Optionally compare the approximation with the exact value.</li>
 * </ul>
 */
public final class FastInverseSqrt {

    private FastInverseSqrt() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the fast inverse square root approximation for a float.
     *
     * @param number the input value (must be positive)
     * @return approximate value of 1 / sqrt(number)
     */
    public static float fastInverseSqrt(float number) {
        float x = number;
        float halfX = 0.5f * x;

        int bits = Float.floatToIntBits(x);
        bits = 0x5f3759df - (bits >> 1); // initial guess
        x = Float.intBitsToFloat(bits);

        // One iteration of Newton-Raphson refinement
        x = x * (1.5f - halfX * x * x);

        return x;
    }

    /**
     * Verifies the fast inverse square root approximation for a float against the exact value.
     *
     * @param number the input value (must be positive)
     * @return {@code true} if the approximation equals the exact value using {@code ==},
     *         {@code false} otherwise
     */
    public static boolean verifyInverseSqrt(float number) {
        float approx = fastInverseSqrt(number);
        float exact = 1.0f / (float) Math.sqrt(number);
        return approx == exact;
    }

    /**
     * Computes the fast inverse square root approximation for a double.
     *
     * @param number the input value (must be positive)
     * @return approximate value of 1 / sqrt(number)
     */
    public static double fastInverseSqrt(double number) {
        double x = number;
        double halfX = 0.5d * x;

        long bits = Double.doubleToLongBits(x);
        bits = 0x5fe6ec85e7de30daL - (bits >> 1); // initial guess
        x = Double.longBitsToDouble(bits);

        // Multiple iterations of Newton-Raphson refinement for higher precision
        for (int iteration = 0; iteration < 4; iteration++) {
            x = x * (1.5d - halfX * x * x);
        }

        return x;
    }

    /**
     * Verifies the fast inverse square root approximation for a double against the exact value.
     *
     * @param number the input value (must be positive)
     * @return {@code true} if the approximation equals the exact value using {@code ==},
     *         {@code false} otherwise
     */
    public static boolean verifyInverseSqrt(double number) {
        double approx = fastInverseSqrt(number);
        double exact = 1.0d / Math.sqrt(number);
        return approx == exact;
    }
}