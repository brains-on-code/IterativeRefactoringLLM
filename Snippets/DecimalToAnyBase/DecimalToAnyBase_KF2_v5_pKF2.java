package com.thealgorithms.conversions;

public final class DecimalToAnyBase {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char ZERO_CHAR = '0';
    private static final char A_CHAR = 'A';
    private static final int DIGIT_OFFSET = 10;

    private DecimalToAnyBase() {
        // Prevent instantiation
    }

    /**
     * Converts a non-negative decimal integer to its representation in the specified base.
     *
     * @param decimal non-negative decimal number to convert
     * @param base    target base (between MIN_BASE and MAX_BASE inclusive)
     * @return string representation of {@code decimal} in the given {@code base}
     * @throws IllegalArgumentException if {@code base} is out of range
     */
    public static String convertToAnyBase(int decimal, int base) {
        validateBase(base);

        if (decimal == 0) {
            return String.valueOf(ZERO_CHAR);
        }

        StringBuilder result = new StringBuilder();

        while (decimal > 0) {
            int remainder = decimal % base;
            result.insert(0, toDigitChar(remainder));
            decimal /= base;
        }

        return result.toString();
    }

    /**
     * Validates that the base is within the supported range.
     *
     * @param base base to validate
     * @throws IllegalArgumentException if {@code base} is less than MIN_BASE or greater than MAX_BASE
     */
    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                "Base must be between " + MIN_BASE + " and " + MAX_BASE
            );
        }
    }

    /**
     * Converts a numeric value (0–35) to its corresponding character representation.
     * Values 0–9 map to '0'–'9', and 10–35 map to 'A'–'Z'.
     *
     * @param value numeric value to convert
     * @return character representation of {@code value}
     */
    private static char toDigitChar(int value) {
        if (value >= 0 && value <= 9) {
            return (char) (ZERO_CHAR + value);
        }
        return (char) (A_CHAR + value - DIGIT_OFFSET);
    }
}