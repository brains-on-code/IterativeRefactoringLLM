package com.thealgorithms.conversions;

public final class AnyBaseToDecimal {

    private static final int DIGIT_CHAR_OFFSET = '0';
    private static final int UPPERCASE_CHAR_OFFSET = 'A' - 10;

    private AnyBaseToDecimal() {
        // Utility class; prevent instantiation
    }

    public static int convertToDecimal(String input, int radix) {
        if (input == null || input.isEmpty()) {
            throw new NumberFormatException("Input string is null or empty");
        }
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid radix: " + radix);
        }

        int result = 0;
        int power = 1;

        for (int i = input.length() - 1; i >= 0; i--) {
            int digit = charToDigit(input.charAt(i));
            if (digit >= radix) {
                throw new NumberFormatException("For input string: " + input);
            }
            result += digit * power;
            power *= radix;
        }

        return result;
    }

    private static int charToDigit(char ch) {
        if (Character.isDigit(ch)) {
            return ch - DIGIT_CHAR_OFFSET;
        }
        if (Character.isUpperCase(ch)) {
            return ch - UPPERCASE_CHAR_OFFSET;
        }
        throw new NumberFormatException("Invalid character: " + ch);
    }
}