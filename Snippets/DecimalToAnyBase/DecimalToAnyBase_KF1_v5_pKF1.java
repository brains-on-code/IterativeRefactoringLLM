package com.thealgorithms.conversions;

import java.util.ArrayList;
import java.util.List;

public final class BaseConverter {
    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char DIGIT_ZERO = '0';
    private static final char LETTER_A = 'A';
    private static final int DECIMAL_BASE = 10;

    private BaseConverter() {
    }

    public static String convertToBase(int number, int targetBase) {
        validateBase(targetBase);

        if (number == 0) {
            return String.valueOf(DIGIT_ZERO);
        }

        List<Character> digits = new ArrayList<>();
        int remaining = number;

        while (remaining > 0) {
            int digit = remaining % targetBase;
            digits.add(toDigitChar(digit));
            remaining /= targetBase;
        }

        StringBuilder result = new StringBuilder(digits.size());
        for (int i = digits.size() - 1; i >= 0; i--) {
            result.append(digits.get(i));
        }

        return result.toString();
    }

    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException("Base must be between " + MIN_BASE + " and " + MAX_BASE);
        }
    }

    private static char toDigitChar(int digit) {
        if (digit >= 0 && digit <= 9) {
            return (char) (DIGIT_ZERO + digit);
        }
        return (char) (LETTER_A + digit - DECIMAL_BASE);
    }
}