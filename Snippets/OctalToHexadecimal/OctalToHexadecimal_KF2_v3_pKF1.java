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

        int decimalValue = 0;
        for (int index = 0; index < octalString.length(); index++) {
            char currentChar = octalString.charAt(index);
            if (currentChar < '0' || currentChar > '7') {
                throw new IllegalArgumentException("Invalid octal digit: " + currentChar);
            }
            int digitValue = currentChar - '0';
            decimalValue = decimalValue * OCTAL_RADIX + digitValue;
        }

        return decimalValue;
    }

    public static String convertDecimalToHexadecimal(int decimalValue) {
        if (decimalValue == 0) {
            return "0";
        }

        StringBuilder hexadecimalBuilder = new StringBuilder();
        int remainingValue = decimalValue;

        while (remainingValue > 0) {
            int remainder = remainingValue % HEX_RADIX;
            hexadecimalBuilder.insert(0, HEX_DIGITS.charAt(remainder));
            remainingValue /= HEX_RADIX;
        }

        return hexadecimalBuilder.toString();
    }
}