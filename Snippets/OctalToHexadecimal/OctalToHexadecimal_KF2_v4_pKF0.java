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
        for (char digitChar : octalNumber.toCharArray()) {
            int digit = digitChar - '0';
            decimalValue = decimalValue * OCTAL_BASE + digit;
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
            hexValue.append(HEX_DIGITS.charAt(digit));
            value /= HEX_BASE;
        }

        return hexValue.reverse().toString();
    }

    private static void validateOctalInput(String octalNumber) {
        if (octalNumber == null || octalNumber.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        for (char currentChar : octalNumber.toCharArray()) {
            if (!isOctalDigit(currentChar)) {
                throw new IllegalArgumentException("Incorrect octal digit: " + currentChar);
            }
        }
    }

    private static boolean isOctalDigit(char digit) {
        return digit >= '0' && digit <= '7';
    }
}