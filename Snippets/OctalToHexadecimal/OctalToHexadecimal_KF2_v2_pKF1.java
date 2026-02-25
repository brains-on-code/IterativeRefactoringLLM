package com.thealgorithms.conversions;

public final class OctalToHexadecimal {

    private static final int OCTAL_BASE = 8;
    private static final int HEX_BASE = 16;
    private static final String HEX_ALPHABET = "0123456789ABCDEF";

    private OctalToHexadecimal() {
        // Utility class; prevent instantiation
    }

    public static int octalToDecimal(String octal) {
        if (octal == null || octal.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int decimal = 0;
        for (int position = 0; position < octal.length(); position++) {
            char digitChar = octal.charAt(position);
            if (digitChar < '0' || digitChar > '7') {
                throw new IllegalArgumentException("Invalid octal digit: " + digitChar);
            }
            int digitValue = digitChar - '0';
            decimal = decimal * OCTAL_BASE + digitValue;
        }

        return decimal;
    }

    public static String decimalToHexadecimal(int decimal) {
        if (decimal == 0) {
            return "0";
        }

        StringBuilder hexResult = new StringBuilder();
        int value = decimal;

        while (value > 0) {
            int remainder = value % HEX_BASE;
            hexResult.insert(0, HEX_ALPHABET.charAt(remainder));
            value /= HEX_BASE;
        }

        return hexResult.toString();
    }
}