package com.thealgorithms.conversions;

public final class AnyBaseToDecimal {

    private static final int DIGIT_CHAR_OFFSET = '0';
    private static final int UPPERCASE_CHAR_OFFSET = 'A' - 10;

    private AnyBaseToDecimal() {}

    public static int convertToDecimal(String input, int radix) {
        validateInput(input, radix);

        int result = 0;
        int positionalMultiplier = 1;

        for (int index = input.length() - 1; index >= 0; index--) {
            int digitValue = charToDigit(input.charAt(index));

            if (digitValue >= radix) {
                throw new NumberFormatException("For input string: " + input);
            }

            result += digitValue * positionalMultiplier;
            positionalMultiplier *= radix;
        }

        return result;
    }

    private static void validateInput(String input, int radix) {
        if (input == null || input.isEmpty()) {
            throw new NumberFormatException("Input string must not be null or empty");
        }
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new NumberFormatException("Invalid radix: " + radix);
        }
    }

    private static int charToDigit(char character) {
        if (Character.isDigit(character)) {
            return character - DIGIT_CHAR_OFFSET;
        }

        if (Character.isUpperCase(character)) {
            return character - UPPERCASE_CHAR_OFFSET;
        }

        throw new NumberFormatException("Invalid character: " + character);
    }
}