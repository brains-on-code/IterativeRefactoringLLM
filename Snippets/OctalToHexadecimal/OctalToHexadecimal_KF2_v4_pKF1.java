package com.thealgorithms.conversions;

public final class OctalToHexadecimal {

    private static final int OCTAL_BASE = 8;
    private static final int HEXADECIMAL_BASE = 16;
    private static final String HEXADECIMAL_DIGITS = "0123456789ABCDEF";

    private OctalToHexadecimal() {
        // Utility class; prevent instantiation
    }

    public static int convertOctalToDecimal(String octalNumber) {
        if (octalNumber == null || octalNumber.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int decimalNumber = 0;
        for (int position = 0; position < octalNumber.length(); position++) {
            char octalDigitChar = octalNumber.charAt(position);
            if (octalDigitChar < '0' || octalDigitChar > '7') {
                throw new IllegalArgumentException("Invalid octal digit: " + octalDigitChar);
            }
            int octalDigit = octalDigitChar - '0';
            decimalNumber = decimalNumber * OCTAL_BASE + octalDigit;
        }

        return decimalNumber;
    }

    public static String convertDecimalToHexadecimal(int decimalNumber) {
        if (decimalNumber == 0) {
            return "0";
        }

        StringBuilder hexadecimalNumber = new StringBuilder();
        int remainingValue = decimalNumber;

        while (remainingValue > 0) {
            int hexDigitIndex = remainingValue % HEXADECIMAL_BASE;
            hexadecimalNumber.insert(0, HEXADECIMAL_DIGITS.charAt(hexDigitIndex));
            remainingValue /= HEXADECIMAL_BASE;
        }

        return hexadecimalNumber.toString();
    }
}