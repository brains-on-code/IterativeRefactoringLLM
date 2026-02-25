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
        float halfValue = 0.5f * value;

        int valueBits = Float.floatToIntBits(value);
        valueBits = 0x5f3759df - (valueBits >> 1);
        float inverseSqrtApproximation = Float.intBitsToFloat(valueBits);

        inverseSqrtApproximation =
                inverseSqrtApproximation * (1.5f - halfValue * inverseSqrtApproximation * inverseSqrtApproximation);

        float exactInverseSqrt = 1.0f / (float) Math.sqrt(value);
        return inverseSqrtApproximation == exactInverseSqrt;
    }

    /**
     * Fast inverse square root approximation for double, compared to exact value.
     *
     * @param value input value
     * @return true if the approximation equals the exact inverse square root
     */
    public static boolean isFastInverseSqrtAccurate(double value) {
        double halfValue = 0.5d * value;

        long valueBits = Double.doubleToLongBits(value);
        valueBits = 0x5fe6ec85e7de30daL - (valueBits >> 1);
        double inverseSqrtApproximation = Double.longBitsToDouble(valueBits);

        for (int iterationIndex = 0; iterationIndex < 4; iterationIndex++) {
            inverseSqrtApproximation =
                    inverseSqrtApproximation * (1.5d - halfValue * inverseSqrtApproximation * inverseSqrtApproximation);
        }

        inverseSqrtApproximation *= value;

        double exactInverseSqrt = 1.0d / Math.sqrt(value);
        return inverseSqrtApproximation == exactInverseSqrt;
    }
}