package com.thealgorithms.conversions;

public final class HexToOct {

    private static final String HEX_DIGITS = "0123456789ABCDEF";
    private static final int HEX_BASE = 16;
    private static final int OCTAL_BASE = 8;

    private HexToOct() {
        // Utility class; prevent instantiation
    }

    public static int hexToDecimal(String hex) {
        validateHexInput(hex);

        String upperHex = hex.toUpperCase();
        int decimalValue = 0;

        for (int i = 0; i < upperHex.length(); i++) {
            int digitValue = getHexDigitValue(upperHex.charAt(i));
            decimalValue = (decimalValue * HEX_BASE) + digitValue;
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
            int remainder = decimal % OCTAL_BASE;
            octalValue += remainder * placeValue;
            decimal /= OCTAL_BASE;
            placeValue *= 10;
        }

        return octalValue;
    }

    public static int hexToOctal(String hex) {
        return decimalToOctal(hexToDecimal(hex));
    }

    private static void validateHexInput(String hex) {
        if (hex == null || hex.isEmpty()) {
            throw new IllegalArgumentException("Hex string must not be null or empty");
        }
    }

    private static int getHexDigitValue(char hexChar) {
        int digitValue = HEX_DIGITS.indexOf(hexChar);
        if (digitValue == -1) {
            throw new IllegalArgumentException("Invalid hex character: " + hexChar);
        }
        return digitValue;
    }
}