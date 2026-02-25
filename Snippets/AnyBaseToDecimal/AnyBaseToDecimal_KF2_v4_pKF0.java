package com.thealgorithms.conversions;

public final class AnyBaseToDecimal {

    private static final int DIGIT_CHAR_OFFSET = '0';
    private static final int UPPERCASE_CHAR_OFFSET = 'A' - 10;

    private AnyBaseToDecimal() {
        // Utility class; prevent instantiation
    }

    public static int convertToDecimal(String input, int radix) {
        validateInput(input, radix);

        int decimalValue = 0;
        int positionalMultiplier = 1;

        for (int index = input.length() - 1; index >= 0; index--) {
            char currentChar = input.charAt(index);
            int digitValue = charToDigit(currentChar, input);

            if (digitValue >= radix) {
                throw new NumberFormatException("For input string: " + input);
            }

            decimalValue += digitValue * positionalMultiplier;
            positionalMultiplier *= radix;
        }

        return decimalValue;
    }

    private static void validateInput(String input, int radix) {
        if (input == null || input.isEmpty()) {
            throw new NumberFormatException("Input string is null or empty");
        }

        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid radix: " + radix);
        }
    }

    private static int charToDigit(char ch, String originalInput) {
        if (Character.isDigit(ch)) {
            return ch - DIGIT_CHAR_OFFSET;
        }

        if (Character.isUpperCase(ch)) {
            return ch - UPPERCASE_CHAR_OFFSET;
        }

        throw new NumberFormatException("Invalid character '" + ch + "' in input string: " + originalInput);
    }
}