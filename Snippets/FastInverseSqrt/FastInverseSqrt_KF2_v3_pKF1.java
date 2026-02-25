package com.thealgorithms.maths;

public final class FastInverseSqrt {

    private FastInverseSqrt() {
        // Utility class; prevent instantiation
    }

    public static boolean inverseSqrt(float input) {
        float estimate = input;
        float halfInput = 0.5f * estimate;

        int bits = Float.floatToIntBits(estimate);
        bits = 0x5f3759df - (bits >> 1);
        estimate = Float.intBitsToFloat(bits);

        estimate = estimate * (1.5f - halfInput * estimate * estimate);

        float expected = 1.0f / (float) Math.sqrt(input);
        return estimate == expected;
    }

    public static boolean inverseSqrt(double input) {
        double estimate = input;
        double halfInput = 0.5d * estimate;

        long bits = Double.doubleToLongBits(estimate);
        bits = 0x5fe6ec85e7de30daL - (bits >> 1);
        estimate = Double.longBitsToDouble(bits);

        for (int i = 0; i < 4; i++) {
            estimate = estimate * (1.5d - halfInput * estimate * estimate);
        }

        estimate *= input;
        double expected = 1.0d / Math.sqrt(input);
        return estimate == expected;
    }
}