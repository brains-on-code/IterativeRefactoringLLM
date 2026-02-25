package com.thealgorithms.conversions;

public final class HexToOct {

    private HexToOct() {
        // Utility class; prevent instantiation
    }

    public static int hexToDecimal(String hex) {
        final String HEX_DIGITS = "0123456789ABCDEF";
        String normalizedHex = hex.toUpperCase();
        int decimal = 0;

        for (int i = 0; i < normalizedHex.length(); i++) {
            char hexChar = normalizedHex.charAt(i);
            int digit = HEX_DIGITS.indexOf(hexChar);
            decimal = 16 * decimal + digit;
        }

        return decimal;
    }

    public static int decimalToOctal(int decimal) {
        int octal = 0;
        int placeValue = 1;

        while (decimal > 0) {
            int remainder = decimal % 8;
            octal += remainder * placeValue;
            decimal /= 8;
            placeValue *= 10;
        }

        return octal;
    }

    public static int hexToOctal(String hex) {
        int decimal = hexToDecimal(hex);
        return decimalToOctal(decimal);
    }
}