package com.thealgorithms.maths;

public final class FastInverseSqrt {

    private FastInverseSqrt() {
        // Utility class; prevent instantiation
    }

    public static boolean inverseSqrt(float number) {
        float estimate = number;
        float halfNumber = 0.5f * estimate;

        int bits = Float.floatToIntBits(estimate);
        bits = 0x5f3759df - (bits >> 1);
        estimate = Float.intBitsToFloat(bits);

        estimate = estimate * (1.5f - halfNumber * estimate * estimate);

        float expected = 1.0f / (float) Math.sqrt(number);
        return estimate == expected;
    }

    public static boolean inverseSqrt(double number) {
        double estimate = number;
        double halfNumber = 0.5d * estimate;

        long bits = Double.doubleToLongBits(estimate);
        bits = 0x5fe6ec85e7de30daL - (bits >> 1);
        estimate = Double.longBitsToDouble(bits);

        for (int iteration = 0; iteration < 4; iteration++) {
            estimate = estimate * (1.5d - halfNumber * estimate * estimate);
        }

        estimate *= number;
        double expected = 1.0d / Math.sqrt(number);
        return estimate == expected;
    }
}