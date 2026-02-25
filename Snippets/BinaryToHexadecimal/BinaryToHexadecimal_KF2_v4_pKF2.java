package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

public final class BinaryToHexadecimal {

    private static final int BITS_PER_HEX_DIGIT = 4;
    private static final int DECIMAL_BASE = 10;
    private static final int HEX_ALPHA_START = 10;
    private static final int HEX_ALPHA_END = 15;

    private BinaryToHexadecimal() {}

    public static String binToHex(int binary) {
        Map<Integer, String> hexLookup = createHexLookupTable();
        StringBuilder hexResult = new StringBuilder();

        while (binary != 0) {
            int decimalValue = 0;

            for (int bitPosition = 0; bitPosition < BITS_PER_HEX_DIGIT; bitPosition++) {
                int currentBit = binary % DECIMAL_BASE;

                if (currentBit > 1) {
                    throw new IllegalArgumentException("Incorrect binary digit: " + currentBit);
                }

                binary /= DECIMAL_BASE;
                decimalValue += currentBit << bitPosition;
            }

            hexResult.insert(0, hexLookup.get(decimalValue));
        }

        return hexResult.length() > 0 ? hexResult.toString() : "0";
    }

    private static Map<Integer, String> createHexLookupTable() {
        Map<Integer, String> hexMap = new HashMap<>();

        for (int i = 0; i < DECIMAL_BASE; i++) {
            hexMap.put(i, String.valueOf(i));
        }

        for (int i = HEX_ALPHA_START; i <= HEX_ALPHA_END; i++) {
            char hexChar = (char) ('A' + i - HEX_ALPHA_START);
            hexMap.put(i, String.valueOf(hexChar));
        }

        return hexMap;
    }
}