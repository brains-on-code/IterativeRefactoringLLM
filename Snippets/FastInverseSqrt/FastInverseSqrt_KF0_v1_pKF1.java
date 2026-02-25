package com.thealgorithms.maths;

/**
 * Program description - To find out the inverse square root of the given number
 * <a href="https://en.wikipedia.org/wiki/Fast_inverse_square_root">Wikipedia</a>
 */
public final class FastInverseSqrt {

    private FastInverseSqrt() {
    }

    /**
     * Returns whether the fast inverse square root (float) matches the exact value
     * 1 / sqrt(number) up to typical float precision (6–8 decimal places).
     */
    public static boolean inverseSqrt(float number) {
        float value = number;
        float halfValue = 0.5f * value;

        int bits = Float.floatToIntBits(value);
        bits = 0x5f3759df - (bits >> 1);
        value = Float.intBitsToFloat(bits);

        value = value * (1.5f - halfValue * value * value);

        float exactInverseSqrt = 1.0f / (float) Math.sqrt(number);
        return value == exactInverseSqrt;
    }

    /**
     * Returns whether the fast inverse square root (double) matches the exact value
     * 1 / sqrt(number) up to typical double precision (14–16 decimal places).
     */
    public static boolean inverseSqrt(double number) {
        double value = number;
        double halfValue = 0.5d * value;

        long bits = Double.doubleToLongBits(value);
        bits = 0x5fe6ec85e7de30daL - (bits >> 1);
        value = Double.longBitsToDouble(bits);

        for (int iteration = 0; iteration < 4; iteration++) {
            value = value * (1.5d - halfValue * value * value);
        }

        double exactInverseSqrt = 1.0d / Math.sqrt(number);
        return value == exactInverseSqrt;
    }
}