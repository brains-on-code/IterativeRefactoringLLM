package com.thealgorithms.conversions;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts non-negative decimal integers to their string representation
 * in bases from 2 to 36.
 */
public final class DecimalToAnyBase {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char ZERO_CHAR = '0';
    private static final char A_CHAR = 'A';
    private static final int DIGIT_OFFSET = 10;

    private DecimalToAnyBase() {
        // Utility class; prevent instantiation.
    }

    /**
     * Converts a non-negative decimal integer to its representation in the given base.
     *
     * <p>Examples:
     * <ul>
     *   <li>{@code convertToAnyBase(10, 2)} returns {@code "1010"}</li>
     *   <li>{@code convertToAnyBase(31, 16)} returns {@code "1F"}</li>
     * </ul>
     *
     * @param decimal non-negative decimal integer to convert
     * @param base    target base, between {@value #MIN_BASE} and {@value #MAX_BASE} inclusive
     * @return string representation of {@code decimal} in the given {@code base}
     * @throws IllegalArgumentException if {@code base} is outside the supported range
     * @throws IllegalArgumentException if {@code decimal} is negative
     */
    public static String convertToAnyBase(int decimal, int base) {
        validateBase(base);
        validateDecimal(decimal);

        if (decimal == 0) {
            return String.valueOf(ZERO_CHAR);
        }

        List<Character> digits = new ArrayList<>();
        int value = decimal;

        while (value > 0) {
            int remainder = value % base;
            digits.add(toDigitChar(remainder));
            value /= base;
        }

        StringBuilder result = new StringBuilder(digits.size());
        for (int i = digits.size() - 1; i >= 0; i--) {
            result.append(digits.get(i));
        }

        return result.toString();
    }

    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                "Base must be between " + MIN_BASE + " and " + MAX_BASE + " (inclusive)"
            );
        }
    }

    private static void validateDecimal(int decimal) {
        if (decimal < 0) {
            throw new IllegalArgumentException("Decimal value must be non-negative");
        }
    }

    /**
     * Maps a numeric digit value (0–35) to its character representation:
     * <ul>
     *   <li>0–9  → '0'–'9'</li>
     *   <li>10–35 → 'A'–'Z'</li>
     * </ul>
     *
     * @param value digit value to convert (0–35)
     * @return character representing the digit
     */
    private static char toDigitChar(int value) {
        if (value >= 0 && value <= 9) {
            return (char) (ZERO_CHAR + value);
        }
        return (char) (A_CHAR + value - DIGIT_OFFSET);
    }
}