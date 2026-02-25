package com.thealgorithms.conversions;

public final class OctalToHexadecimal {

    private static final int OCTAL_RADIX = 8;
    private static final int HEX_RADIX = 16;
    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private OctalToHexadecimal() {
        // Utility class; prevent instantiation
    }

    public static int octalToDecimal(String octal) {
        if (octal == null || octal.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int decimal = 0;
        for (int index = 0; index < octal.length(); index++) {
            char digitChar = octal.charAt(index);
            if (digitChar < '0' || digitChar > '7') {
                throw new IllegalArgumentException("Invalid octal digit: " + digitChar);
            }
            int digit = digitChar - '0';
            decimal = decimal * OCTAL_RADIX + digit;
        }

        return decimal;
    }

    public static String decimalToHex(int decimal) {
        if (decimal == 0) {
            return "0";
        }

        StringBuilder hexBuilder = new StringBuilder();
        int value = decimal;

        while (value > 0) {
            int digitIndex = value % HEX_RADIX;
            hexBuilder.insert(0, HEX_DIGITS.charAt(digitIndex));
            value /= HEX_RADIX;
        }

        return hexBuilder.toString();
    }
}