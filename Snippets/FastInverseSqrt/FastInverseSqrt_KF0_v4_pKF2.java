package com.thealgorithms.maths;

/**
 * Fast inverse square root implementation.
 *
 * <p>Based on the algorithm popularized by Quake III Arena:
 * https://en.wikipedia.org/wiki/Fast_inverse_square_root
 */
public final class FastInverseSqrt {

    /** Magic constant used for the float bit-level approximation. */
    private static final int FLOAT_MAGIC = 0x5f3759df;

    /** Magic constant used for the double bit-level approximation. */
    private static final long DOUBLE_MAGIC = 0x5fe6ec85e7de30daL;

    /** Number of Newton–Raphson refinement iterations for double precision. */
    private static final int DOUBLE_REFINEMENT_STEPS = 4;

    private FastInverseSqrt() {
        // Prevent instantiation of utility class
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
        float halfX = 0.5f * x;

        // Initial approximation via bit-level hack
        int bits = Float.floatToIntBits(x);
        bits = FLOAT_MAGIC - (bits >> 1);
        x = Float.intBitsToFloat(bits);

        // One Newton–Raphson refinement step
        x = x * (1.5f - halfX * x * x);

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
        double halfX = 0.5d * x;

        // Initial approximation via bit-level hack
        long bits = Double.doubleToLongBits(x);
        bits = DOUBLE_MAGIC - (bits >> 1);
        x = Double.longBitsToDouble(bits);

        // Multiple Newton–Raphson refinement steps
        for (int i = 0; i < DOUBLE_REFINEMENT_STEPS; i++) {
            x = x * (1.5d - halfX * x * x);
        }

        return x;
    }
}