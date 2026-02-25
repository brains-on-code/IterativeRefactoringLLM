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
        if (targetBase < MIN_BASE || targetBase > MAX_BASE) {
            throw new IllegalArgumentException("Base must be between " + MIN_BASE + " and " + MAX_BASE);
        }

        if (number == 0) {
            return String.valueOf(DIGIT_ZERO);
        }

        List<Character> digits = new ArrayList<>();
        int remaining = number;

        while (remaining > 0) {
            int digitValue = remaining % targetBase;
            digits.add(toDigitChar(digitValue));
            remaining /= targetBase;
        }

        StringBuilder convertedNumber = new StringBuilder(digits.size());
        for (int i = digits.size() - 1; i >= 0; i--) {
            convertedNumber.append(digits.get(i));
        }

        return convertedNumber.toString();
    }

    private static char toDigitChar(int digitValue) {
        if (digitValue >= 0 && digitValue <= 9) {
            return (char) (DIGIT_ZERO + digitValue);
        } else {
            return (char) (LETTER_A + digitValue - DECIMAL_BASE);
        }
    }
}