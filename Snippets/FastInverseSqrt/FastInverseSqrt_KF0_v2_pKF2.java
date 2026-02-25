package com.thealgorithms.maths;

/**
 * Fast inverse square root implementation.
 *
 * <p>Based on the algorithm popularized by Quake III Arena:
 * https://en.wikipedia.org/wiki/Fast_inverse_square_root
 */
public final class FastInverseSqrt {

    private FastInverseSqrt() {
        // Prevent instantiation
    }

    /**
     * Approximates the inverse square root (1 / sqrt(x)) for a float.
     *
     * <p>Typical accuracy: 6–8 decimal places.
     *
     * @param number positive float input
     * @return approximate value of 1 / sqrt(number)
     */
    public static float inverseSqrt(float number) {
        float x = number;
        float xHalf = 0.5f * x;

        // Bit-level initial approximation
        int bits = Float.floatToIntBits(x);
        bits = 0x5f3759df - (bits >> 1);
        x = Float.intBitsToFloat(bits);

        // Single Newton–Raphson refinement step
        x = x * (1.5f - xHalf * x * x);

        return x;
    }

    /**
     * Approximates the inverse square root (1 / sqrt(x)) for a double.
     *
     * <p>Typical accuracy: 14–16 decimal places after several refinement steps.
     *
     * @param number positive double input
     * @return approximate value of 1 / sqrt(number)
     */
    public static double inverseSqrt(double number) {
        double x = number;
        double xHalf = 0.5d * x;

        // Bit-level initial approximation
        long bits = Double.doubleToLongBits(x);
        bits = 0x5fe6ec85e7de30daL - (bits >> 1);
        x = Double.longBitsToDouble(bits);

        // Newton–Raphson refinement (4 iterations for higher precision)
        for (int i = 0; i < 4; i++) {
            x = x * (1.5d - xHalf * x * x);
        }

        return x;
    }
}