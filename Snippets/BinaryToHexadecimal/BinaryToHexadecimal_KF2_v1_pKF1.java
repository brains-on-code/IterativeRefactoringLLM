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

    public static String convertBinaryToHex(int binaryNumber) {
        Map<Integer, String> decimalToHexDigitMap = createDecimalToHexDigitMap();
        StringBuilder hexResult = new StringBuilder();

        while (binaryNumber != 0) {
            int decimalValueForHexDigit = 0;

            for (int bitPosition = 0; bitPosition < BITS_PER_HEX_DIGIT; bitPosition++) {
                int currentBit = binaryNumber % DECIMAL_BASE;
                if (currentBit > 1) {
                    throw new IllegalArgumentException("Incorrect binary digit: " + currentBit);
                }
                binaryNumber /= DECIMAL_BASE;
                decimalValueForHexDigit += (int) (currentBit * Math.pow(BINARY_BASE, bitPosition));
            }

            hexResult.insert(0, decimalToHexDigitMap.get(decimalValueForHexDigit));
        }

        return hexResult.length() > 0 ? hexResult.toString() : "0";
    }

    private static Map<Integer, String> createDecimalToHexDigitMap() {
        Map<Integer, String> decimalToHexDigitMap = new HashMap<>();

        for (int decimalValue = 0; decimalValue < DECIMAL_BASE; decimalValue++) {
            decimalToHexDigitMap.put(decimalValue, String.valueOf(decimalValue));
        }

        for (int decimalValue = HEX_ALPHA_START_DECIMAL; decimalValue <= HEX_ALPHA_END_DECIMAL; decimalValue++) {
            char hexChar = (char) ('A' + decimalValue - HEX_ALPHA_START_DECIMAL);
            decimalToHexDigitMap.put(decimalValue, String.valueOf(hexChar));
        }

        return decimalToHexDigitMap;
    }
}