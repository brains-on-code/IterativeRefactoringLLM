package com.thealgorithms.maths;

/**
 * Fast inverse square root implementation.
 *
 * <p>Based on the algorithm popularized by Quake III Arena:
 * https://en.wikipedia.org/wiki/Fast_inverse_square_root
 */
public final class FastInverseSqrt {

    private FastInverseSqrt() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes an approximation of the inverse square root (1 / sqrt(x)) for a float.
     *
     * <p>Accuracy: typically 6–8 decimal places.
     *
     * @param number positive float input
     * @return approximate value of 1 / sqrt(number)
     */
    public static float inverseSqrt(float number) {
        float x = number;
        float xHalf = 0.5f * x;

        // Initial guess using bit-level hack
        int bits = Float.floatToIntBits(x);
        bits = 0x5f3759df - (bits >> 1);
        x = Float.intBitsToFloat(bits);

        // One iteration of Newton–Raphson refinement
        x = x * (1.5f - xHalf * x * x);

        return x;
    }

    /**
     * Computes an approximation of the inverse square root (1 / sqrt(x)) for a double.
     *
     * <p>Accuracy: typically 14–16 decimal places after several Newton–Raphson iterations.
     *
     * @param number positive double input
     * @return approximate value of 1 / sqrt(number)
     */
    public static double inverseSqrt(double number) {
        double x = number;
        double xHalf = 0.5d * x;

        // Initial guess using bit-level hack
        long bits = Double.doubleToLongBits(x);
        bits = 0x5fe6ec85e7de30daL - (bits >> 1);
        x = Double.longBitsToDouble(bits);

        // Multiple Newton–Raphson refinement iterations for higher precision
        for (int i = 0; i < 4; i++) {
            x = x * (1.5d - xHalf * x * x);
        }

        return x;
    }
}