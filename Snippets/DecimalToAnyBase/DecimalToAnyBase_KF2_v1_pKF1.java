package com.thealgorithms.conversions;

import java.util.ArrayList;
import java.util.List;

public final class DecimalToAnyBase {

    private static final int MINIMUM_BASE = 2;
    private static final int MAXIMUM_BASE = 36;
    private static final char DIGIT_ZERO = '0';
    private static final char LETTER_A = 'A';
    private static final int LETTER_DIGIT_START = 10;

    private DecimalToAnyBase() {
    }

    public static String convertToBase(int decimalNumber, int targetBase) {
        if (targetBase < MINIMUM_BASE || targetBase > MAXIMUM_BASE) {
            throw new IllegalArgumentException(
                "Base must be between " + MINIMUM_BASE + " and " + MAXIMUM_BASE
            );
        }

        if (decimalNumber == 0) {
            return String.valueOf(DIGIT_ZERO);
        }

        List<Character> baseDigits = new ArrayList<>();
        int remainingValue = decimalNumber;

        while (remainingValue > 0) {
            int remainder = remainingValue % targetBase;
            baseDigits.add(mapDigitToChar(remainder));
            remainingValue /= targetBase;
        }

        StringBuilder convertedNumber = new StringBuilder(baseDigits.size());
        for (int index = baseDigits.size() - 1; index >= 0; index--) {
            convertedNumber.append(baseDigits.get(index));
        }

        return convertedNumber.toString();
    }

    private static char mapDigitToChar(int digitValue) {
        if (digitValue >= 0 && digitValue <= 9) {
            return (char) (DIGIT_ZERO + digitValue);
        } else {
            return (char) (LETTER_A + digitValue - LETTER_DIGIT_START);
        }
    }
}