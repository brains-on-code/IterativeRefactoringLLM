package com.thealgorithms.conversions;

import java.util.ArrayList;
import java.util.List;

public final class DecimalToAnyBase {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char ZERO_CHAR = '0';
    private static final char FIRST_LETTER_CHAR = 'A';
    private static final int LETTER_DIGIT_OFFSET = 10;

    private DecimalToAnyBase() {
    }

    public static String convertToBase(int decimalNumber, int targetBase) {
        if (targetBase < MIN_BASE || targetBase > MAX_BASE) {
            throw new IllegalArgumentException(
                "Base must be between " + MIN_BASE + " and " + MAX_BASE
            );
        }

        if (decimalNumber == 0) {
            return String.valueOf(ZERO_CHAR);
        }

        List<Character> baseDigits = new ArrayList<>();
        int remainingValue = decimalNumber;

        while (remainingValue > 0) {
            int remainder = remainingValue % targetBase;
            baseDigits.add(convertDigitToChar(remainder));
            remainingValue /= targetBase;
        }

        StringBuilder convertedNumber = new StringBuilder(baseDigits.size());
        for (int i = baseDigits.size() - 1; i >= 0; i--) {
            convertedNumber.append(baseDigits.get(i));
        }

        return convertedNumber.toString();
    }

    private static char convertDigitToChar(int digitValue) {
        if (digitValue >= 0 && digitValue <= 9) {
            return (char) (ZERO_CHAR + digitValue);
        } else {
            return (char) (FIRST_LETTER_CHAR + digitValue - LETTER_DIGIT_OFFSET);
        }
    }
}