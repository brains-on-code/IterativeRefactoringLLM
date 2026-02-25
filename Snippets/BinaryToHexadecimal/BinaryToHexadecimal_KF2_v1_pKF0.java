package com.thealgorithms.conversions;

public final class BinaryToHexadecimal {

    private static final int BITS_IN_HEX_DIGIT = 4;
    private static final int BASE_BINARY = 2;
    private static final int BASE_DECIMAL = 10;

    private BinaryToHexadecimal() {
        // Utility class
    }

    public static String binToHex(int binary) {
        if (binary == 0) {
            return "0";
        }

        StringBuilder hex = new StringBuilder();

        while (binary != 0) {
            int decimalValue = 0;

            for (int bitIndex = 0; bitIndex < BITS_IN_HEX_DIGIT && binary != 0; bitIndex++) {
                int currentBit = binary % BASE_DECIMAL;

                if (currentBit != 0 && currentBit != 1) {
                    throw new IllegalArgumentException("Incorrect binary digit: " + currentBit);
                }

                binary /= BASE_DECIMAL;
                decimalValue += currentBit << bitIndex; // currentBit * 2^bitIndex
            }

            hex.insert(0, toHexDigit(decimalValue));
        }

        return hex.toString();
    }

    private static char toHexDigit(int value) {
        if (value >= 0 && value <= 9) {
            return (char) ('0' + value);
        }
        if (value >= 10 && value <= 15) {
            return (char) ('A' + (value - 10));
        }
        throw new IllegalArgumentException("Invalid value for a hex digit: " + value);
    }
}