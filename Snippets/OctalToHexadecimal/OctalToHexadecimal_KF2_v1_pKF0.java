package com.thealgorithms.conversions;

public final class OctalToHexadecimal {

    private static final int OCTAL_BASE = 8;
    private static final int HEX_BASE = 16;
    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private OctalToHexadecimal() {
        // Utility class; prevent instantiation
    }

    public static int octalToDecimal(String octalNumber) {
        validateOctalInput(octalNumber);

        int decimalValue = 0;
        for (int i = 0; i < octalNumber.length(); i++) {
            int currentDigit = octalNumber.charAt(i) - '0';
            decimalValue = decimalValue * OCTAL_BASE + currentDigit;
        }

        return decimalValue;
    }

    public static String decimalToHexadecimal(int decimalNumber) {
        if (decimalNumber == 0) {
            return "0";
        }

        StringBuilder hexValue = new StringBuilder();
        int value = decimalNumber;

        while (value > 0) {
            int digit = value % HEX_BASE;
            hexValue.insert(0, HEX_DIGITS.charAt(digit));
            value /= HEX_BASE;
        }

        return hexValue.toString();
    }

    private static void validateOctalInput(String octalNumber) {
        if (octalNumber == null || octalNumber.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        for (int i = 0; i < octalNumber.length(); i++) {
            char currentChar = octalNumber.charAt(i);
            if (currentChar < '0' || currentChar > '7') {
                throw new IllegalArgumentException("Incorrect octal digit: " + currentChar);
            }
        }
    }
}