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
        float originalValue = value;
        float halfValue = 0.5f * originalValue;

        int bitRepresentation = Float.floatToIntBits(originalValue);
        bitRepresentation = 0x5f3759df - (bitRepresentation >> 1);
        float approximation = Float.intBitsToFloat(bitRepresentation);

        approximation = approximation * (1.5f - halfValue * approximation * approximation);

        return approximation == (1.0f / (float) Math.sqrt(value));
    }

    /**
     * Fast inverse square root approximation for double, compared to exact value.
     *
     * @param value input value
     * @return true if the approximation equals the exact inverse square root
     */
    public static boolean isFastInverseSqrtAccurate(double value) {
        double originalValue = value;
        double halfValue = 0.5d * originalValue;

        long bitRepresentation = Double.doubleToLongBits(originalValue);
        bitRepresentation = 0x5fe6ec85e7de30daL - (bitRepresentation >> 1);
        double approximation = Double.longBitsToDouble(bitRepresentation);

        for (int iteration = 0; iteration < 4; iteration++) {
            approximation = approximation * (1.5d - halfValue * approximation * approximation);
        }

        approximation *= value;

        return approximation == 1.0d / Math.sqrt(value);
    }
}