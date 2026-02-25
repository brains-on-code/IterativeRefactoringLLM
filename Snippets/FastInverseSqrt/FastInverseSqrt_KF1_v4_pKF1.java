package com.thealgorithms.maths;

/**
 * Utility class for fast inverse square root related methods.
 */
public final class FastInverseSquareRoot {

    private FastInverseSquareRoot() {}

    /**
     * Fast inverse square root approximation for float, compared to exact value.
     *
     * @param value input value
     * @return true if the approximation equals the exact inverse square root
     */
    public static boolean isFastInverseSqrtAccurate(float value) {
        float halfInput = 0.5f * value;

        int inputBits = Float.floatToIntBits(value);
        inputBits = 0x5f3759df - (inputBits >> 1);
        float inverseSqrtEstimate = Float.intBitsToFloat(inputBits);

        inverseSqrtEstimate =
                inverseSqrtEstimate * (1.5f - halfInput * inverseSqrtEstimate * inverseSqrtEstimate);

        float exactInverseSqrt = 1.0f / (float) Math.sqrt(value);
        return inverseSqrtEstimate == exactInverseSqrt;
    }

    /**
     * Fast inverse square root approximation for double, compared to exact value.
     *
     * @param value input value
     * @return true if the approximation equals the exact inverse square root
     */
    public static boolean isFastInverseSqrtAccurate(double value) {
        double halfInput = 0.5d * value;

        long inputBits = Double.doubleToLongBits(value);
        inputBits = 0x5fe6ec85e7de30daL - (inputBits >> 1);
        double inverseSqrtEstimate = Double.longBitsToDouble(inputBits);

        for (int iteration = 0; iteration < 4; iteration++) {
            inverseSqrtEstimate =
                    inverseSqrtEstimate * (1.5d - halfInput * inverseSqrtEstimate * inverseSqrtEstimate);
        }

        inverseSqrtEstimate *= value;

        double exactInverseSqrt = 1.0d / Math.sqrt(value);
        return inverseSqrtEstimate == exactInverseSqrt;
    }
}