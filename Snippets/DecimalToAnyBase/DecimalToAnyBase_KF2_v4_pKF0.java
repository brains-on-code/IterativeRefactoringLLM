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

    public static String convertToAnyBase(int decimal, int base) {
        validateBase(base);

        if (decimal == 0) {
            return String.valueOf(ZERO_CHAR);
        }

        StringBuilder result = new StringBuilder();
        int remainingValue = decimal;

        while (remainingValue > 0) {
            int remainder = remainingValue % base;
            result.append(toDigitChar(remainder));
            remainingValue /= base;
        }

        return result.reverse().toString();
    }

    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                String.format("Base must be between %d and %d (inclusive)", MIN_BASE, MAX_BASE)
            );
        }
    }

    private static char toDigitChar(int digit) {
        if (digit >= 0 && digit <= 9) {
            return (char) (ZERO_CHAR + digit);
        }
        return (char) (A_CHAR + digit - DIGIT_OFFSET);
    }
}