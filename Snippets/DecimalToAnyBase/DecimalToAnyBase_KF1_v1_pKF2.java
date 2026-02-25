package com.thealgorithms.conversions;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for converting integers to their string representation
 * in an arbitrary base between 2 and 36.
 */
public final class Class1 {
    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char DIGIT_ZERO = '0';
    private static final char LETTER_A = 'A';
    private static final int DECIMAL_BASE = 10;

    private Class1() {
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
    public static String method1(int value, int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException("Base must be between " + MIN_BASE + " and " + MAX_BASE);
        }

        if (value == 0) {
            return String.valueOf(DIGIT_ZERO);
        }

        List<Character> digits = new ArrayList<>();
        while (value > 0) {
            digits.add(toDigitChar(value % base));
            value /= base;
        }

        StringBuilder result = new StringBuilder(digits.size());
        for (int i = digits.size() - 1; i >= 0; i--) {
            result.append(digits.get(i));
        }

        return result.toString();
    }

    /**
     * Converts a numeric digit (0–35) to its corresponding character.
     * 0–9 map to '0'–'9', and 10–35 map to 'A'–'Z'.
     *
     * @param digit the digit to convert (0–35)
     * @return the character representation of the digit
     */
    private static char toDigitChar(int digit) {
        if (digit >= 0 && digit <= 9) {
            return (char) (DIGIT_ZERO + digit);
        } else {
            return (char) (LETTER_A + digit - DECIMAL_BASE);
        }
    }
}