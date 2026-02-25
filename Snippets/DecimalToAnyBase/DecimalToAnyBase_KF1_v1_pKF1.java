package com.thealgorithms.conversions;

import java.util.ArrayList;
import java.util.List;

public final class Class1 {
    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char ZERO_CHAR = '0';
    private static final char UPPERCASE_A_CHAR = 'A';
    private static final int DECIMAL_BASE = 10;

    private Class1() {
    }

    public static String convertToBase(int number, int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException("Base must be between " + MIN_BASE + " and " + MAX_BASE);
        }

        if (number == 0) {
            return String.valueOf(ZERO_CHAR);
        }

        List<Character> digits = new ArrayList<>();
        while (number > 0) {
            digits.add(digitToChar(number % base));
            number /= base;
        }

        StringBuilder result = new StringBuilder(digits.size());
        for (int index = digits.size() - 1; index >= 0; index--) {
            result.append(digits.get(index));
        }

        return result.toString();
    }

    private static char digitToChar(int digit) {
        if (digit >= 0 && digit <= 9) {
            return (char) (ZERO_CHAR + digit);
        } else {
            return (char) (UPPERCASE_A_CHAR + digit - DECIMAL_BASE);
        }
    }
}