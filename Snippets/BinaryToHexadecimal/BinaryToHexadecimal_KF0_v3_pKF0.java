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
    private static final int DECIMAL_BASE = 10;
    private static final int HEX_START_DECIMAL = 10;
    private static final int HEX_END_DECIMAL = 15;
    private static final int BINARY_GROUP_DIVISOR = 10_000; // 10^4

    private static final Map<Integer, String> DECIMAL_TO_HEX = initDecimalToHexMap();

    private BinaryToHexadecimal() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts a binary number to a hexadecimal number.
     *
     * @param binary The binary number to convert.
     * @return The hexadecimal representation of the binary number.
     * @throws IllegalArgumentException If the binary number contains digits other than 0 and 1.
     */
    public static String binToHex(int binary) {
        if (binary == 0) {
            return "0";
        }

        StringBuilder hexBuilder = new StringBuilder();
        int remainingBinary = binary;

        while (remainingBinary != 0) {
            int decimalValue = extractNextHexDigitValue(remainingBinary);
            hexBuilder.insert(0, DECIMAL_TO_HEX.get(decimalValue));
            remainingBinary /= BINARY_GROUP_DIVISOR;
        }

        return hexBuilder.toString();
    }

    /**
     * Extracts the decimal value of the next 4-bit group from the binary number.
     *
     * @param binary The binary number.
     * @return The decimal value of the next 4-bit group.
     */
    private static int extractNextHexDigitValue(int binary) {
        int decimalValue = 0;
        int tempBinary = binary;

        for (int bitPosition = 0; bitPosition < BITS_PER_HEX_DIGIT && tempBinary != 0; bitPosition++) {
            int currentBit = tempBinary % DECIMAL_BASE;
            validateBinaryDigit(currentBit);
            tempBinary /= DECIMAL_BASE;
            decimalValue |= (currentBit << bitPosition);
        }

        return decimalValue;
    }

    /**
     * Validates that the given digit is a valid binary digit (0 or 1).
     *
     * @param digit The digit to validate.
     * @throws IllegalArgumentException If the digit is not 0 or 1.
     */
    private static void validateBinaryDigit(int digit) {
        if (digit != 0 && digit != 1) {
            throw new IllegalArgumentException("Incorrect binary digit: " + digit);
        }
    }

    /**
     * Initializes the hexadecimal map with decimal to hexadecimal mappings.
     *
     * @return The initialized map containing mappings from decimal numbers to hexadecimal digits.
     */
    private static Map<Integer, String> initDecimalToHexMap() {
        Map<Integer, String> hexMap = new HashMap<>();

        for (int i = 0; i < DECIMAL_BASE; i++) {
            hexMap.put(i, String.valueOf(i));
        }

        for (int i = HEX_START_DECIMAL; i <= HEX_END_DECIMAL; i++) {
            char hexChar = (char) ('A' + i - HEX_START_DECIMAL);
            hexMap.put(i, String.valueOf(hexChar));
        }

        return hexMap;
    }
}