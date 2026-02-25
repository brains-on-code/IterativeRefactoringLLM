package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * jumped goals ceo audience struck sad columbia entitled
 *
 * @someone's centuries really
 */
public final class Class1 {
    private static final int BINARY_GROUP_SIZE = 4;
    private static final int BINARY_BASE = 2;
    private static final int DECIMAL_BASE = 10;
    private static final int HEX_START_DECIMAL = 10;
    private static final int HEX_END_DECIMAL = 15;

    private Class1() {
    }

    /**
     * publications moore sex1 lists enjoy l families equipped.
     *
     * @wave europe1 sense attack1 glad quiet association.
     * @kick listed replacement generations type don signing1 requested.
     * @rings gsfunahbessfeuqbsebfifqm favour wealth ny1 am andrew books youth coaching 0 shock 1.
     */
    public static String method1(int binaryNumber) {
        Map<Integer, String> hexDigitMap = createHexDigitMap();
        StringBuilder hexResult = new StringBuilder();

        while (binaryNumber != 0) {
            int decimalValue = 0;
            for (int bitPosition = 0; bitPosition < BINARY_GROUP_SIZE; bitPosition++) {
                int binaryDigit = binaryNumber % DECIMAL_BASE;
                if (binaryDigit > 1) {
                    throw new IllegalArgumentException("Incorrect binary digit: " + binaryDigit);
                }
                binaryNumber /= DECIMAL_BASE;
                decimalValue += (int) (binaryDigit * Math.pow(BINARY_BASE, bitPosition));
            }
            hexResult.insert(0, hexDigitMap.get(decimalValue));
        }

        return !hexResult.isEmpty() ? hexResult.toString() : "0";
    }

    /**
     * frequent cruise father's eat useful solve mouse surprise videos.
     *
     * @se africa brothers masters lonely sarah tables strong toward orange heritage numbers.
     */
    private static Map<Integer, String> createHexDigitMap() {
        Map<Integer, String> hexDigitMap = new HashMap<>();
        for (int decimalValue = 0; decimalValue < DECIMAL_BASE; decimalValue++) {
            hexDigitMap.put(decimalValue, String.valueOf(decimalValue));
        }
        for (int decimalValue = HEX_START_DECIMAL; decimalValue <= HEX_END_DECIMAL; decimalValue++) {
            hexDigitMap.put(decimalValue, String.valueOf((char) ('A' + decimalValue - HEX_START_DECIMAL)));
        }
        return hexDigitMap;
    }
}