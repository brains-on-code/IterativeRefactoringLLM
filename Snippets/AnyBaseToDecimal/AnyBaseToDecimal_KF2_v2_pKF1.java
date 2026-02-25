package com.thealgorithms.conversions;

public final class AnyBaseToDecimal {

    private static final int NUMERIC_CHAR_OFFSET = '0';
    private static final int UPPERCASE_ALPHA_OFFSET = 'A' - 10;

    private AnyBaseToDecimal() {
        // Utility class; prevent instantiation
    }

    public static int convertToDecimal(String numberInBase, int base) {
        int decimalResult = 0;
        int placeValue = 1;

        for (int index = numberInBase.length() - 1; index >= 0; index--) {
            char currentChar = numberInBase.charAt(index);
            int digitValue = parseDigit(currentChar);

            if (digitValue >= base) {
                throw new NumberFormatException("For input string: " + numberInBase);
            }

            decimalResult += digitValue * placeValue;
            placeValue *= base;
        }

        return decimalResult;
    }

    private static int parseDigit(char digitChar) {
        if (Character.isDigit(digitChar)) {
            return digitChar - NUMERIC_CHAR_OFFSET;
        } else if (Character.isUpperCase(digitChar)) {
            return digitChar - UPPERCASE_ALPHA_OFFSET;
        } else {
            throw new NumberFormatException("Invalid character: " + digitChar);
        }
    }
}