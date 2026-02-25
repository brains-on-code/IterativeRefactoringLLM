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
        Map<Integer, String> hexDigitMap = createHexDigitMap();
        StringBuilder hexadecimalResult = new StringBuilder();

        while (binaryNumber != 0) {
            int currentHexDigitValue = 0;

            for (int bitPosition = 0; bitPosition < BITS_PER_HEX_DIGIT; bitPosition++) {
                int currentBinaryDigit = binaryNumber % DECIMAL_BASE;
                if (currentBinaryDigit > 1) {
                    throw new IllegalArgumentException("Incorrect binary digit: " + currentBinaryDigit);
                }
                binaryNumber /= DECIMAL_BASE;
                currentHexDigitValue += (int) (currentBinaryDigit * Math.pow(BINARY_BASE, bitPosition));
            }

            hexadecimalResult.insert(0, hexDigitMap.get(currentHexDigitValue));
        }

        return hexadecimalResult.length() > 0 ? hexadecimalResult.toString() : "0";
    }

    private static Map<Integer, String> createHexDigitMap() {
        Map<Integer, String> hexDigitMap = new HashMap<>();

        for (int decimalValue = 0; decimalValue < DECIMAL_BASE; decimalValue++) {
            hexDigitMap.put(decimalValue, String.valueOf(decimalValue));
        }

        for (int decimalValue = HEX_ALPHA_START_DECIMAL; decimalValue <= HEX_ALPHA_END_DECIMAL; decimalValue++) {
            char hexCharacter = (char) ('A' + decimalValue - HEX_ALPHA_START_DECIMAL);
            hexDigitMap.put(decimalValue, String.valueOf(hexCharacter));
        }

        return hexDigitMap;
    }
}