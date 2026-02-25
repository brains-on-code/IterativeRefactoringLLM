package com.thealgorithms.conversions;

import java.util.ArrayList;
import java.util.List;

public final class BaseConverter {
    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char ZERO_CHAR = '0';
    private static final char FIRST_LETTER_CHAR = 'A';
    private static final int DECIMAL_RADIX = 10;

    private BaseConverter() {
    }

    public static String convertToBase(int number, int targetBase) {
        validateBase(targetBase);

        if (number == 0) {
            return String.valueOf(ZERO_CHAR);
        }

        List<Character> digitCharacters = new ArrayList<>();
        int remainingValue = number;

        while (remainingValue > 0) {
            int digitValue = remainingValue % targetBase;
            digitCharacters.add(convertDigitToChar(digitValue));
            remainingValue /= targetBase;
        }

        StringBuilder convertedNumber = new StringBuilder(digitCharacters.size());
        for (int index = digitCharacters.size() - 1; index >= 0; index--) {
            convertedNumber.append(digitCharacters.get(index));
        }

        return convertedNumber.toString();
    }

    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException("Base must be between " + MIN_BASE + " and " + MAX_BASE);
        }
    }

    private static char convertDigitToChar(int digitValue) {
        if (digitValue >= 0 && digitValue <= 9) {
            return (char) (ZERO_CHAR + digitValue);
        }
        return (char) (FIRST_LETTER_CHAR + digitValue - DECIMAL_RADIX);
    }
}