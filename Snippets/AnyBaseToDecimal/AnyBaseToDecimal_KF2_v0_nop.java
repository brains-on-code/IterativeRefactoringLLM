package com.thealgorithms.conversions;


public final class AnyBaseToDecimal {
    private static final int CHAR_OFFSET_FOR_DIGIT = '0';
    private static final int CHAR_OFFSET_FOR_UPPERCASE = 'A' - 10;

    private AnyBaseToDecimal() {
    }


    public static int convertToDecimal(String input, int radix) {
        int result = 0;
        int power = 1;

        for (int i = input.length() - 1; i >= 0; i--) {
            int digit = valOfChar(input.charAt(i));
            if (digit >= radix) {
                throw new NumberFormatException("For input string: " + input);
            }
            result += digit * power;
            power *= radix;
        }
        return result;
    }


    private static int valOfChar(char character) {
        if (Character.isDigit(character)) {
            return character - CHAR_OFFSET_FOR_DIGIT;
        } else if (Character.isUpperCase(character)) {
            return character - CHAR_OFFSET_FOR_UPPERCASE;
        } else {
            throw new NumberFormatException("invalid character:" + character);
        }
    }
}
