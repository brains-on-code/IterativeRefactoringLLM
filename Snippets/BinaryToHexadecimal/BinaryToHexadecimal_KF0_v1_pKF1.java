package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts any Binary Number to a Hexadecimal Number
 *
 * @author Nishita Aggarwal
 */
public final class BinaryToHexadecimal {
    private static final int BITS_PER_HEX_DIGIT = 4;
    private static final int BINARY_BASE = 2;
    private static final int DECIMAL_BASE = 10;
    private static final int HEX_ALPHA_START_DECIMAL = 10;
    private static final int HEX_ALPHA_END_DECIMAL = 15;

    private BinaryToHexadecimal() {
    }

    /**
     * Converts a binary number to a hexadecimal number.
     *
     * @param binaryInput The binary number to convert.
     * @return The hexadecimal representation of the binary number.
     * @throws IllegalArgumentException If the binary number contains digits other than 0 and 1.
     */
    public static String binToHex(int binaryInput) {
        Map<Integer, String> decimalToHexDigitMap = createDecimalToHexDigitMap();
        StringBuilder hexResult = new StringBuilder();
        int remainingBinary = binaryInput;

        while (remainingBinary != 0) {
            int decimalValueForGroup = 0;
            for (int bitPosition = 0; bitPosition < BITS_PER_HEX_DIGIT; bitPosition++) {
                int currentBit = remainingBinary % DECIMAL_BASE;
                if (currentBit > 1) {
                    throw new IllegalArgumentException("Incorrect binary digit: " + currentBit);
                }
                remainingBinary /= DECIMAL_BASE;
                decimalValueForGroup += (int) (currentBit * Math.pow(BINARY_BASE, bitPosition));
            }
            hexResult.insert(0, decimalToHexDigitMap.get(decimalValueForGroup));
        }

        return !hexResult.isEmpty() ? hexResult.toString() : "0";
    }

    /**
     * Initializes the hexadecimal map with decimal to hexadecimal mappings.
     *
     * @return The initialized map containing mappings from decimal numbers to hexadecimal digits.
     */
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