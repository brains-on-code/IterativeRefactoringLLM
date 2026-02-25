package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

public final class BinaryToHexadecimal {

    private static final int BITS_PER_HEX_DIGIT = 4;
    private static final int BINARY_BASE = 2;
    private static final int DECIMAL_BASE = 10;
    private static final int HEX_ALPHA_START_DECIMAL = 10;
    private static final int HEX_ALPHA_END_DECIMAL = 15;

    private BinaryToHexadecimal() {
    }

    public static String convertBinaryToHex(int binaryInput) {
        Map<Integer, String> hexDigitLookup = createHexDigitLookup();
        StringBuilder hexResult = new StringBuilder();

        while (binaryInput != 0) {
            int hexDigitDecimalValue = 0;

            for (int bitIndex = 0; bitIndex < BITS_PER_HEX_DIGIT; bitIndex++) {
                int leastSignificantDigit = binaryInput % DECIMAL_BASE;
                if (leastSignificantDigit > 1) {
                    throw new IllegalArgumentException("Incorrect binary digit: " + leastSignificantDigit);
                }
                binaryInput /= DECIMAL_BASE;
                hexDigitDecimalValue += (int) (leastSignificantDigit * Math.pow(BINARY_BASE, bitIndex));
            }

            hexResult.insert(0, hexDigitLookup.get(hexDigitDecimalValue));
        }

        return hexResult.length() > 0 ? hexResult.toString() : "0";
    }

    private static Map<Integer, String> createHexDigitLookup() {
        Map<Integer, String> hexDigitLookup = new HashMap<>();

        for (int decimalValue = 0; decimalValue < DECIMAL_BASE; decimalValue++) {
            hexDigitLookup.put(decimalValue, String.valueOf(decimalValue));
        }

        for (int decimalValue = HEX_ALPHA_START_DECIMAL; decimalValue <= HEX_ALPHA_END_DECIMAL; decimalValue++) {
            char hexChar = (char) ('A' + decimalValue - HEX_ALPHA_START_DECIMAL);
            hexDigitLookup.put(decimalValue, String.valueOf(hexChar));
        }

        return hexDigitLookup;
    }
}