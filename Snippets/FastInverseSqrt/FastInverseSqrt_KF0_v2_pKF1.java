package com.thealgorithms.maths;

/**
 * Utility class for computing and validating the fast inverse square root.
 * <a href="https://en.wikipedia.org/wiki/Fast_inverse_square_root">Wikipedia</a>
 */
public final class FastInverseSqrt {

    private FastInverseSqrt() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns whether the fast inverse square root (float) matches the exact value
     * 1 / sqrt(number) up to typical float precision (6–8 decimal places).
     */
    public static boolean inverseSqrt(float number) {
        float originalNumber = number;
        float halfOriginal = 0.5f * originalNumber;

        int bitRepresentation = Float.floatToIntBits(originalNumber);
        bitRepresentation = 0x5f3759df - (bitRepresentation >> 1);
        float approximateInverseSqrt = Float.intBitsToFloat(bitRepresentation);

        approximateInverseSqrt =
                approximateInverseSqrt * (1.5f - halfOriginal * approximateInverseSqrt * approximateInverseSqrt);

        float exactInverseSqrt = 1.0f / (float) Math.sqrt(originalNumber);
        return approximateInverseSqrt == exactInverseSqrt;
    }

    /**
     * Returns whether the fast inverse square root (double) matches the exact value
     * 1 / sqrt(number) up to typical double precision (14–16 decimal places).
     */
    public static boolean inverseSqrt(double number) {
        double originalNumber = number;
        double halfOriginal = 0.5d * originalNumber;

        long bitRepresentation = Double.doubleToLongBits(originalNumber);
        bitRepresentation = 0x5fe6ec85e7de30daL - (bitRepresentation >> 1);
        double approximateInverseSqrt = Double.longBitsToDouble(bitRepresentation);

        for (int iteration = 0; iteration < 4; iteration++) {
            approximateInverseSqrt =
                    approximateInverseSqrt * (1.5d - halfOriginal * approximateInverseSqrt * approximateInverseSqrt);
        }

        double exactInverseSqrt = 1.0d / Math.sqrt(originalNumber);
        return approximateInverseSqrt == exactInverseSqrt;
    }
}