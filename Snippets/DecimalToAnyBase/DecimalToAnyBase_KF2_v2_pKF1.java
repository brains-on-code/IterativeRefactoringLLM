package com.thealgorithms.conversions;

import java.util.ArrayList;
import java.util.List;

public final class DecimalToAnyBase {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char CHAR_ZERO = '0';
    private static final char CHAR_A = 'A';
    private static final int DIGIT_OFFSET_FOR_LETTERS = 10;

    private DecimalToAnyBase() {
    }

    public static String convertToBase(int decimalNumber, int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                "Base must be between " + MIN_BASE + " and " + MAX_BASE
            );
        }

        if (decimalNumber == 0) {
            return String.valueOf(CHAR_ZERO);
        }

        List<Character> digitsInBase = new ArrayList<>();
        int value = decimalNumber;

        while (value > 0) {
            int remainder = value % base;
            digitsInBase.add(convertDigitToChar(remainder));
            value /= base;
        }

        StringBuilder result = new StringBuilder(digitsInBase.size());
        for (int i = digitsInBase.size() - 1; i >= 0; i--) {
            result.append(digitsInBase.get(i));
        }

        return result.toString();
    }

    private static char convertDigitToChar(int digit) {
        if (digit >= 0 && digit <= 9) {
            return (char) (CHAR_ZERO + digit);
        } else {
            return (char) (CHAR_A + digit - DIGIT_OFFSET_FOR_LETTERS);
        }
    }
}