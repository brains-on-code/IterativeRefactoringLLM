package com.thealgorithms.conversions;

public final class HexToOct {

    private HexToOct() {
        // Utility class; prevent instantiation
    }

    public static int convertHexToDecimal(String hexString) {
        final String HEX_DIGITS = "0123456789ABCDEF";
        String normalizedHex = hexString.toUpperCase();
        int decimalValue = 0;

        for (int index = 0; index < normalizedHex.length(); index++) {
            char currentHexChar = normalizedHex.charAt(index);
            int digitValue = HEX_DIGITS.indexOf(currentHexChar);
            decimalValue = 16 * decimalValue + digitValue;
        }

        return decimalValue;
    }

    public static int convertDecimalToOctal(int decimalNumber) {
        int octalValue = 0;
        int placeMultiplier = 1;

        while (decimalNumber > 0) {
            int remainder = decimalNumber % 8;
            octalValue += remainder * placeMultiplier;
            decimalNumber /= 8;
            placeMultiplier *= 10;
        }

        return octalValue;
    }

    public static int convertHexToOctal(String hexString) {
        int decimalValue = convertHexToDecimal(hexString);
        return convertDecimalToOctal(decimalValue);
    }
}