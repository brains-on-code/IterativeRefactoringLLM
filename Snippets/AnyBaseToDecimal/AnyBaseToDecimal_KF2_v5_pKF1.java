package com.thealgorithms.conversions;

public final class AnyBaseToDecimal {

    private static final int NUMERIC_CHAR_OFFSET = '0';
    private static final int UPPERCASE_LETTER_OFFSET = 'A' - 10;

    private AnyBaseToDecimal() {
        // Utility class; prevent instantiation
    }

    public static int convertToDecimal(String value, int base) {
        int decimalValue = 0;
        int currentPlaceMultiplier = 1;

        for (int index = value.length() - 1; index >= 0; index--) {
            char currentCharacter = value.charAt(index);
            int digit = parseDigit(currentCharacter);

            if (digit >= base) {
                throw new NumberFormatException("For input string: " + value);
            }

            decimalValue += digit * currentPlaceMultiplier;
            currentPlaceMultiplier *= base;
        }

        return decimalValue;
    }

    private static int parseDigit(char character) {
        if (Character.isDigit(character)) {
            return character - NUMERIC_CHAR_OFFSET;
        } else if (Character.isUpperCase(character)) {
            return character - UPPERCASE_LETTER_OFFSET;
        } else {
            throw new NumberFormatException("Invalid character: " + character);
        }
    }
}