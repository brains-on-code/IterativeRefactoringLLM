package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting binary numbers to hexadecimal representation.
 */
public final class BinaryToHexConverter {

    private static final int BITS_PER_HEX_DIGIT = 4;
    private static final int DECIMAL_BASE = 10;
    private static final int HEX_DIGIT_COUNT = 16;
    private static final int HEX_START_DECIMAL = 10;
    private static final int HEX_END_DECIMAL = 15;

    private static final Map<Integer, String> HEX_DIGIT_MAP = createHexDigitMap();

    private BinaryToHexConverter() {
        // Prevent instantiation
    }

    /**
     * Converts a binary number (given as an int) to its hexadecimal string representation.
     *
     * @param binaryNumber the binary number to convert
     * @return the hexadecimal representation of the given binary number
     * @throws IllegalArgumentException if the input contains digits other than 0 or 1
     */
    public static String toHex(int binaryNumber) {
        if (binaryNumber == 0) {
            return "0";
        }

        StringBuilder hexResult = new StringBuilder();
        int remainingBinary = binaryNumber;

        while (remainingBinary != 0) {
            int decimalValue = 0;

            for (int bitPosition = 0;
                 bitPosition < BITS_PER_HEX_DIGIT && remainingBinary != 0;
                 bitPosition++) {

                int digit = remainingBinary % DECIMAL_BASE;
                validateBinaryDigit(digit);

                remainingBinary /= DECIMAL_BASE;
                decimalValue += digit << bitPosition; // digit * 2^bitPosition
            }

            hexResult.insert(0, HEX_DIGIT_MAP.get(decimalValue));
        }

        return hexResult.toString();
    }

    private static void validateBinaryDigit(int digit) {
        if (digit != 0 && digit != 1) {
            throw new IllegalArgumentException("Incorrect binary digit: " + digit);
        }
    }

    /**
     * Creates a mapping from decimal values 0–15 to their hexadecimal string representations.
     *
     * @return a map of decimal values to hexadecimal characters
     */
    private static Map<Integer, String> createHexDigitMap() {
        Map<Integer, String> hexDigitMap = new HashMap<>(HEX_DIGIT_COUNT);

        for (int value = 0; value < DECIMAL_BASE; value++) {
            hexDigitMap.put(value, Integer.toString(value));
        }

        for (int value = HEX_START_DECIMAL; value <= HEX_END_DECIMAL; value++) {
            char hexChar = (char) ('A' + value - HEX_START_DECIMAL);
            hexDigitMap.put(value, Character.toString(hexChar));
        }

        return hexDigitMap;
    }
}