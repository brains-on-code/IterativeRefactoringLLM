package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting binary numbers to hexadecimal.
 */
public final class BinaryToHexadecimal {

    private static final int BITS_PER_HEX_DIGIT = 4;
    private static final int BINARY_BASE = 2;
    private static final int DECIMAL_BASE = 10;
    private static final int HEX_ALPHA_START = 10;
    private static final int HEX_ALPHA_END = 15;

    private BinaryToHexadecimal() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts a binary number to its hexadecimal representation.
     *
     * @param binary the binary number to convert
     * @return the hexadecimal representation of the given binary number
     * @throws IllegalArgumentException if the input contains digits other than 0 or 1
     */
    public static String binToHex(int binary) {
        Map<Integer, String> hexMap = createHexMap();
        StringBuilder hexResult = new StringBuilder();

        while (binary != 0) {
            int decimalValue = 0;

            for (int bitIndex = 0; bitIndex < BITS_PER_HEX_DIGIT; bitIndex++) {
                int currentBit = binary % DECIMAL_BASE;

                if (currentBit > 1) {
                    throw new IllegalArgumentException("Incorrect binary digit: " + currentBit);
                }

                binary /= DECIMAL_BASE;
                decimalValue += currentBit * (int) Math.pow(BINARY_BASE, bitIndex);
            }

            hexResult.insert(0, hexMap.get(decimalValue));
        }

        return hexResult.length() > 0 ? hexResult.toString() : "0";
    }

    /**
     * Builds a lookup table mapping decimal values (0–15) to hexadecimal digits ("0"–"F").
     *
     * @return a map from decimal values to hexadecimal digits
     */
    private static Map<Integer, String> createHexMap() {
        Map<Integer, String> hexMap = new HashMap<>();

        // Map 0–9 to "0"–"9"
        for (int i = 0; i < DECIMAL_BASE; i++) {
            hexMap.put(i, String.valueOf(i));
        }

        // Map 10–15 to "A"–"F"
        for (int i = HEX_ALPHA_START; i <= HEX_ALPHA_END; i++) {
            char hexChar = (char) ('A' + i - HEX_ALPHA_START);
            hexMap.put(i, String.valueOf(hexChar));
        }

        return hexMap;
    }
}