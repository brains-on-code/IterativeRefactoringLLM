package com.thealgorithms.maths;

public final class FastInverseSqrt {

    private FastInverseSqrt() {
        // Utility class; prevent instantiation
    }

    public static boolean inverseSqrt(float value) {
        float currentEstimate = value;
        float halfValue = 0.5f * currentEstimate;

        int bitRepresentation = Float.floatToIntBits(currentEstimate);
        bitRepresentation = 0x5f3759df - (bitRepresentation >> 1);
        currentEstimate = Float.intBitsToFloat(bitRepresentation);

        currentEstimate = currentEstimate * (1.5f - halfValue * currentEstimate * currentEstimate);

        float expectedResult = 1.0f / (float) Math.sqrt(value);
        return currentEstimate == expectedResult;
    }

    public static boolean inverseSqrt(double value) {
        double currentEstimate = value;
        double halfValue = 0.5d * currentEstimate;

        long bitRepresentation = Double.doubleToLongBits(currentEstimate);
        bitRepresentation = 0x5fe6ec85e7de30daL - (bitRepresentation >> 1);
        currentEstimate = Double.longBitsToDouble(bitRepresentation);

        for (int iteration = 0; iteration < 4; iteration++) {
            currentEstimate = currentEstimate * (1.5d - halfValue * currentEstimate * currentEstimate);
        }

        currentEstimate *= value;
        double expectedResult = 1.0d / Math.sqrt(value);
        return currentEstimate == expectedResult;
    }
}