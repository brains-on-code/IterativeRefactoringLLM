package com.thealgorithms.conversions;

public final class HexToOct {

    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private HexToOct() {
        // Utility class; prevent instantiation
    }

    public static int convertHexToDecimal(String hex) {
        String normalizedHex = hex.toUpperCase();
        int decimalValue = 0;

        for (int index = 0; index < normalizedHex.length(); index++) {
            char currentHexChar = normalizedHex.charAt(index);
            int digitValue = HEX_DIGITS.indexOf(currentHexChar);
            decimalValue = 16 * decimalValue + digitValue;
        }

        return decimalValue;
    }

    public static int convertDecimalToOctal(int decimal) {
        int octalValue = 0;
        int placeValue = 1;

        while (decimal > 0) {
            int remainder = decimal % 8;
            octalValue += remainder * placeValue;
            decimal /= 8;
            placeValue *= 10;
        }

        return octalValue;
    }

    public static int convertHexToOctal(String hex) {
        int decimalValue = convertHexToDecimal(hex);
        return convertDecimalToOctal(decimalValue);
    }
}