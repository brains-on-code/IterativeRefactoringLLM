package com.thealgorithms.conversions;

public final class DecimalToAnyBase {
    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char ZERO_CHAR = '0';
    private static final char A_CHAR = 'A';
    private static final int DIGIT_OFFSET = 10;

    private DecimalToAnyBase() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts a non-negative decimal integer to its representation in the specified base.
     *
     * @param decimal the non-negative decimal number to convert
     * @param base    the target base (between 2 and 36 inclusive)
     * @return the string representation of {@code decimal} in the given {@code base}
     * @throws IllegalArgumentException if {@code base} is outside the range [2, 36]
     */
    public static String convertToAnyBase(int decimal, int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                "Base must be between " + MIN_BASE + " and " + MAX_BASE
            );
        }

        if (decimal == 0) {
            return String.valueOf(ZERO_CHAR);
        }

        StringBuilder result = new StringBuilder();

        // Repeatedly divide by base and prepend the corresponding digit character
        while (decimal > 0) {
            int remainder = decimal % base;
            result.insert(0, toDigitChar(remainder));
            decimal /= base;
        }

        return result.toString();
    }

    /**
     * Maps a numeric digit value (0–35) to its character representation.
     * 0–9 map to '0'–'9', 10–35 map to 'A'–'Z'.
     *
     * @param value the digit value to convert
     * @return the corresponding character
     */
    private static char toDigitChar(int value) {
        if (value >= 0 && value <= 9) {
            return (char) (ZERO_CHAR + value);
        }
        return (char) (A_CHAR + value - DIGIT_OFFSET);
    }
}