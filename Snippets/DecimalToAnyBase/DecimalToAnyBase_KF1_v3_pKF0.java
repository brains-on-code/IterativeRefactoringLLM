package com.thealgorithms.conversions;

public final class IntegerBaseConverter {
    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char DIGIT_ZERO = '0';
    private static final char LETTER_A = 'A';
    private static final int DECIMAL_BASE = 10;

    private IntegerBaseConverter() {
        // Prevent instantiation
    }

    /**
     * Converts a non-negative integer to its string representation in the specified base.
     *
     * @param value the non-negative integer to convert
     * @param base  the base for conversion (between 2 and 36 inclusive)
     * @return the string representation of {@code value} in the given {@code base}
     * @throws IllegalArgumentException if {@code base} is not between 2 and 36
     */
    public static String convertToBase(int value, int base) {
        validateBase(base);

        if (value == 0) {
            return String.valueOf(DIGIT_ZERO);
        }

        StringBuilder result = new StringBuilder();
        int remaining = value;

        while (remaining > 0) {
            int digit = remaining % base;
            result.append(toDigitChar(digit));
            remaining /= base;
        }

        return result.reverse().toString();
    }

    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                "Base must be between " + MIN_BASE + " and " + MAX_BASE
            );
        }
    }

    /**
     * Maps a numeric digit (0–35) to its character representation:
     * 0–9 -> '0'–'9', 10–35 -> 'A'–'Z'.
     *
     * @param digit the digit to convert (0–35)
     * @return the corresponding character
     */
    private static char toDigitChar(int digit) {
        if (digit >= 0 && digit <= 9) {
            return (char) (DIGIT_ZERO + digit);
        }
        return (char) (LETTER_A + digit - DECIMAL_BASE);
    }
}