package com.thealgorithms.conversions;

public final class OctalToHexadecimal {

    private static final int OCTAL_RADIX = 8;
    private static final int HEX_RADIX = 16;
    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private OctalToHexadecimal() {
        // Utility class; prevent instantiation
    }

    public static int convertOctalToDecimal(String octalString) {
        if (octalString == null || octalString.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int decimalResult = 0;
        for (int index = 0; index < octalString.length(); index++) {
            char octalChar = octalString.charAt(index);
            if (octalChar < '0' || octalChar > '7') {
                throw new IllegalArgumentException("Invalid octal digit: " + octalChar);
            }
            int octalDigit = octalChar - '0';
            decimalResult = decimalResult * OCTAL_RADIX + octalDigit;
        }

        return decimalResult;
    }

    public static String convertDecimalToHexadecimal(int decimalValue) {
        if (decimalValue == 0) {
            return "0";
        }

        StringBuilder hexBuilder = new StringBuilder();
        int remainingValue = decimalValue;

        while (remainingValue > 0) {
            int hexDigitIndex = remainingValue % HEX_RADIX;
            hexBuilder.insert(0, HEX_DIGITS.charAt(hexDigitIndex));
            remainingValue /= HEX_RADIX;
        }

        return hexBuilder.toString();
    }
}