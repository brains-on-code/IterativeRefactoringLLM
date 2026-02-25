package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting a binary number to its hexadecimal representation.
 */
public final class Class1 {
    private static final int BITS_PER_HEX_DIGIT = 4;
    private static final int BINARY_BASE = 2;
    private static final int DECIMAL_BASE = 10;
    private static final int HEX_START_DECIMAL = 10;
    private static final int HEX_END_DECIMAL = 15;

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Converts a binary number (given as an int) to its hexadecimal string representation.
     *
     * @param binaryNumber the binary number to convert (e.g., 1010 for decimal 10)
     * @return the hexadecimal representation of the given binary number
     * @throws IllegalArgumentException if the input contains digits other than 0 or 1
     */
    public static String method1(int binaryNumber) {
        Map<Integer, String> hexDigitMap = createHexDigitMap();
        StringBuilder hexResult = new StringBuilder();

        while (binaryNumber != 0) {
            int decimalValueOfGroup = 0;

            // Process 4 binary digits (one hex digit) at a time
            for (int bitPosition = 0; bitPosition < BITS_PER_HEX_DIGIT; bitPosition++) {
                int binaryDigit = binaryNumber % DECIMAL_BASE;

                if (binaryDigit > 1) {
                    throw new IllegalArgumentException("Incorrect binary digit: " + binaryDigit);
                }

                binaryNumber /= DECIMAL_BASE;
                decimalValueOfGroup += (int) (binaryDigit * Math.pow(BINARY_BASE, bitPosition));
            }

            hexResult.insert(0, hexDigitMap.get(decimalValueOfGroup));
        }

        return !hexResult.isEmpty() ? hexResult.toString() : "0";
    }

    /**
     * Creates a mapping from decimal values 0–15 to their hexadecimal string equivalents.
     *
     * @return a map where keys are decimal values and values are hex digit strings
     */
    private static Map<Integer, String> createHexDigitMap() {
        Map<Integer, String> hexDigitMap = new HashMap<>();

        // Map 0–9 to "0"–"9"
        for (int value = 0; value < DECIMAL_BASE; value++) {
            hexDigitMap.put(value, String.valueOf(value));
        }

        // Map 10–15 to "A"–"F"
        for (int value = HEX_START_DECIMAL; value <= HEX_END_DECIMAL; value++) {
            hexDigitMap.put(value, String.valueOf((char) ('A' + value - HEX_START_DECIMAL)));
        }

        return hexDigitMap;
    }
}