package com.thealgorithms.conversions;

import java.util.ArrayList;
import java.util.List;

public final class BaseConverter {
    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char ZERO = '0';
    private static final char UPPERCASE_A = 'A';
    private static final int DECIMAL_RADIX = 10;

    private BaseConverter() {
    }

    public static String convertToBase(int value, int targetBase) {
        if (targetBase < MIN_BASE || targetBase > MAX_BASE) {
            throw new IllegalArgumentException("Base must be between " + MIN_BASE + " and " + MAX_BASE);
        }

        if (value == 0) {
            return String.valueOf(ZERO);
        }

        List<Character> digitCharacters = new ArrayList<>();
        int remainingValue = value;

        while (remainingValue > 0) {
            int digit = remainingValue % targetBase;
            digitCharacters.add(convertDigitToChar(digit));
            remainingValue /= targetBase;
        }

        StringBuilder result = new StringBuilder(digitCharacters.size());
        for (int i = digitCharacters.size() - 1; i >= 0; i--) {
            result.append(digitCharacters.get(i));
        }

        return result.toString();
    }

    private static char convertDigitToChar(int digit) {
        if (digit >= 0 && digit <= 9) {
            return (char) (ZERO + digit);
        } else {
            return (char) (UPPERCASE_A + digit - DECIMAL_RADIX);
        }
    }
}