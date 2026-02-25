package com.thealgorithms.conversions;

import java.util.ArrayList;
import java.util.List;


public final class DecimalToAnyBase {
    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char ZERO_CHAR = '0';
    private static final char A_CHAR = 'A';
    private static final int DIGIT_OFFSET = 10;

    private DecimalToAnyBase() {
    }


    public static String convertToAnyBase(int decimal, int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException("Base must be between " + MIN_BASE + " and " + MAX_BASE);
        }

        if (decimal == 0) {
            return String.valueOf(ZERO_CHAR);
        }

        List<Character> digits = new ArrayList<>();
        while (decimal > 0) {
            digits.add(convertToChar(decimal % base));
            decimal /= base;
        }

        StringBuilder result = new StringBuilder(digits.size());
        for (int i = digits.size() - 1; i >= 0; i--) {
            result.append(digits.get(i));
        }

        return result.toString();
    }


    private static char convertToChar(int value) {
        if (value >= 0 && value <= 9) {
            return (char) (ZERO_CHAR + value);
        } else {
            return (char) (A_CHAR + value - DIGIT_OFFSET);
        }
    }
}
