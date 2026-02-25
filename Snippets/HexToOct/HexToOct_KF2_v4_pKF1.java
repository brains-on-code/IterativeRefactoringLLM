package com.thealgorithms.conversions;

public final class HexToOct {

    private HexToOct() {
        // Utility class; prevent instantiation
    }

    public static int hexToDecimal(String hexString) {
        final String HEX_DIGITS = "0123456789ABCDEF";
        String normalizedHex = hexString.toUpperCase();
        int decimalResult = 0;

        for (int i = 0; i < normalizedHex.length(); i++) {
            char hexChar = normalizedHex.charAt(i);
            int digitValue = HEX_DIGITS.indexOf(hexChar);
            decimalResult = 16 * decimalResult + digitValue;
        }

        return decimalResult;
    }

    public static int decimalToOctal(int decimalNumber) {
        int octalResult = 0;
        int placeMultiplier = 1;

        while (decimalNumber > 0) {
            int remainder = decimalNumber % 8;
            octalResult += remainder * placeMultiplier;
            decimalNumber /= 8;
            placeMultiplier *= 10;
        }

        return octalResult;
    }

    public static int hexToOctal(String hexString) {
        int decimalValue = hexToDecimal(hexString);
        return decimalToOctal(decimalValue);
    }
}