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
        Map<Integer, String> decimalToHexDigit = createDecimalToHexDigitMap();
        StringBuilder hexResult = new StringBuilder();
        int remainingBinary = binaryInput;

        while (remainingBinary != 0) {
            int nibbleDecimalValue = 0;
            for (int bitIndex = 0; bitIndex < BITS_PER_HEX_DIGIT; bitIndex++) {
                int binaryDigit = remainingBinary % DECIMAL_BASE;
                if (binaryDigit > 1) {
                    throw new IllegalArgumentException("Incorrect binary digit: " + binaryDigit);
                }
                remainingBinary /= DECIMAL_BASE;
                nibbleDecimalValue += (int) (binaryDigit * Math.pow(BINARY_BASE, bitIndex));
            }
            hexResult.insert(0, decimalToHexDigit.get(nibbleDecimalValue));
        }

        return !hexResult.isEmpty() ? hexResult.toString() : "0";
    }

    /**
     * Initializes the hexadecimal map with decimal to hexadecimal mappings.
     *
     * @return The initialized map containing mappings from decimal numbers to hexadecimal digits.
     */
    private static Map<Integer, String> createDecimalToHexDigitMap() {
        Map<Integer, String> decimalToHexDigit = new HashMap<>();
        for (int decimalDigit = 0; decimalDigit < DECIMAL_BASE; decimalDigit++) {
            decimalToHexDigit.put(decimalDigit, String.valueOf(decimalDigit));
        }
        for (int decimalDigit = HEX_ALPHA_START_DECIMAL; decimalDigit <= HEX_ALPHA_END_DECIMAL; decimalDigit++) {
            char hexCharacter = (char) ('A' + decimalDigit - HEX_ALPHA_START_DECIMAL);
            decimalToHexDigit.put(decimalDigit, String.valueOf(hexCharacter));
        }
        return decimalToHexDigit;
    }
}