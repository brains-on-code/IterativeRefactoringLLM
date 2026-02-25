package com.thealgorithms.conversions;

public final class AnyBaseToDecimal {

    private static final int DIGIT_CHAR_OFFSET = '0';
    private static final int UPPERCASE_CHAR_OFFSET = 'A' - 10;

    private AnyBaseToDecimal() {
        // Utility class; prevent instantiation
    }

    public static int convertToDecimal(String number, int base) {
        int decimalValue = 0;
        int positionalMultiplier = 1;

        for (int index = number.length() - 1; index >= 0; index--) {
            int digitValue = getDigitValue(number.charAt(index));
            if (digitValue >= base) {
                throw new NumberFormatException("For input string: " + number);
            }
            decimalValue += digitValue * positionalMultiplier;
            positionalMultiplier *= base;
        }
        return decimalValue;
    }

    private static int getDigitValue(char character) {
        if (Character.isDigit(character)) {
            return character - DIGIT_CHAR_OFFSET;
        } else if (Character.isUpperCase(character)) {
            return character - UPPERCASE_CHAR_OFFSET;
        } else {
            throw new NumberFormatException("Invalid character: " + character);
        }
    }
}