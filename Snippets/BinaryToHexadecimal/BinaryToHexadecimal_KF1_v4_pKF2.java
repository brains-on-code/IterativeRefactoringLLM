package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting a binary number to its hexadecimal representation.
 */
public final class BinaryToHexConverter {

    private static final int BITS_PER_HEX_DIGIT = 4;
    private static final int BINARY_BASE = 2;
    private static final int DECIMAL_BASE = 10;
    private static final int HEX_START_DECIMAL = 10;
    private static final int HEX_END_DECIMAL = 15;

    private BinaryToHexConverter() {
        // Utility class; prevent instantiation.
    }

    /**
     * Converts a binary number (given as an int) to its hexadecimal string representation.
     *
     * @param binaryNumber the binary number to convert (e.g., 1010 for decimal 10)
     * @return the hexadecimal representation of the given binary number
     * @throws IllegalArgumentException if the input contains digits other than 0 or 1
     */
    public static String toHex(int binaryNumber) {
        Map<Integer, String> hexDigitMap = createHexDigitMap();
        StringBuilder hexResult = new StringBuilder();

        while (binaryNumber != 0) {
            int decimalValueOfGroup = 0;

            for (int bitPosition = 0; bitPosition < BITS_PER_HEX_DIGIT; bitPosition++) {
                int binaryDigit = binaryNumber % DECIMAL_BASE;

                if (binaryDigit > 1) {
                    throw new IllegalArgumentException("Incorrect binary digit: " + binaryDigit);
                }

                binaryNumber /= DECIMAL_BASE;
                decimalValueOfGroup += binaryDigit * (int) Math.pow(BINARY_BASE, bitPosition);
            }

            hexResult.insert(0, hexDigitMap.get(decimalValueOfGroup));
        }

        return hexResult.length() > 0 ? hexResult.toString() : "0";
    }

    /**
     * Builds a map from decimal values 0–15 to their hexadecimal string equivalents.
     */
    private static Map<Integer, String> createHexDigitMap() {
        Map<Integer, String> hexDigitMap = new HashMap<>();

        for (int value = 0; value < DECIMAL_BASE; value++) {
            hexDigitMap.put(value, String.valueOf(value));
        }

        for (int value = HEX_START_DECIMAL; value <= HEX_END_DECIMAL; value++) {
            char hexChar = (char) ('A' + value - HEX_START_DECIMAL);
            hexDigitMap.put(value, String.valueOf(hexChar));
        }

        return hexDigitMap;
    }
}