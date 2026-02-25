package com.thealgorithms.conversions;

public final class BinaryToHexadecimal {

    private static final int BITS_IN_HEX_DIGIT = 4;
    private static final int DECIMAL_BASE = 10;

    private BinaryToHexadecimal() {
        // Utility class
    }

    public static String binToHex(int binary) {
        if (binary == 0) {
            return "0";
        }

        StringBuilder hexResult = new StringBuilder();

        while (binary != 0) {
            int decimalValue = extractNextNibbleAsDecimal(binary);
            hexResult.insert(0, toHexDigit(decimalValue));
            binary = removeProcessedBits(binary);
        }

        return hexResult.toString();
    }

    private static int extractNextNibbleAsDecimal(int binary) {
        int decimalValue = 0;
        int bitIndex = 0;
        int tempBinary = binary;

        while (bitIndex < BITS_IN_HEX_DIGIT && tempBinary != 0) {
            int currentBit = tempBinary % DECIMAL_BASE;
            validateBinaryDigit(currentBit);

            decimalValue += currentBit << bitIndex;
            tempBinary /= DECIMAL_BASE;
            bitIndex++;
        }

        return decimalValue;
    }

    private static int removeProcessedBits(int binary) {
        int bitsProcessed = 0;

        while (bitsProcessed < BITS_IN_HEX_DIGIT && binary != 0) {
            binary /= DECIMAL_BASE;
            bitsProcessed++;
        }

        return binary;
    }

    private static void validateBinaryDigit(int digit) {
        if (digit != 0 && digit != 1) {
            throw new IllegalArgumentException("Incorrect binary digit: " + digit);
        }
    }

    private static char toHexDigit(int value) {
        if (value >= 0 && value <= 9) {
            return (char) ('0' + value);
        }
        if (value >= 10 && value <= 15) {
            return (char) ('A' + (value - 10));
        }
        throw new IllegalArgumentException("Invalid value for a hex digit: " + value);
    }
}