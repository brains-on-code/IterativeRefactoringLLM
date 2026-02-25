package com.thealgorithms.conversions;

public final class HexToOct {

    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private HexToOct() {
        // Utility class; prevent instantiation
    }

    public static int hexToDecimal(String hex) {
        if (hex == null || hex.isEmpty()) {
            throw new IllegalArgumentException("Hex string must not be null or empty");
        }

        String upperHex = hex.toUpperCase();
        int decimalValue = 0;

        for (int i = 0; i < upperHex.length(); i++) {
            char hexChar = upperHex.charAt(i);
            int digitValue = HEX_DIGITS.indexOf(hexChar);

            if (digitValue == -1) {
                throw new IllegalArgumentException("Invalid hex character: " + hexChar);
            }

            decimalValue = (decimalValue * 16) + digitValue;
        }

        return decimalValue;
    }

    public static int decimalToOctal(int decimal) {
        if (decimal < 0) {
            throw new IllegalArgumentException("Decimal value must be non-negative");
        }

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

    public static int hexToOctal(String hex) {
        int decimalValue = hexToDecimal(hex);
        return decimalToOctal(decimalValue);
    }
}