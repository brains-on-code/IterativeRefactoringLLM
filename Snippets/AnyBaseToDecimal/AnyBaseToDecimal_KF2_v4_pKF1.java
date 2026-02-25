package com.thealgorithms.conversions;

public final class AnyBaseToDecimal {

    private static final int DIGIT_OFFSET = '0';
    private static final int UPPERCASE_ALPHA_OFFSET = 'A' - 10;

    private AnyBaseToDecimal() {
        // Utility class; prevent instantiation
    }

    public static int convertToDecimal(String numberInBase, int base) {
        int decimalResult = 0;
        int placeValue = 1;

        for (int position = numberInBase.length() - 1; position >= 0; position--) {
            char currentChar = numberInBase.charAt(position);
            int digitValue = toDigitValue(currentChar);

            if (digitValue >= base) {
                throw new NumberFormatException("For input string: " + numberInBase);
            }

            decimalResult += digitValue * placeValue;
            placeValue *= base;
        }

        return decimalResult;
    }

    private static int toDigitValue(char ch) {
        if (Character.isDigit(ch)) {
            return ch - DIGIT_OFFSET;
        } else if (Character.isUpperCase(ch)) {
            return ch - UPPERCASE_ALPHA_OFFSET;
        } else {
            throw new NumberFormatException("Invalid character: " + ch);
        }
    }
}