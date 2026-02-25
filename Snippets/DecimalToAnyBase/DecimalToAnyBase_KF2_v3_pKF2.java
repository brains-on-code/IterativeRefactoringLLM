package com.thealgorithms.conversions;

public final class DecimalToAnyBase {
    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char ZERO_CHAR = '0';
    private static final char A_CHAR = 'A';
    private static final int DIGIT_OFFSET = 10;

    private DecimalToAnyBase() {}

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

    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                "Base must be between " + MIN_BASE + " and " + MAX_BASE
            );
        }
    }

    private static char toDigitChar(int value) {
        if (value >= 0 && value <= 9) {
            return (char) (ZERO_CHAR + value);
        }
        return (char) (A_CHAR + value - DIGIT_OFFSET);
    }
}