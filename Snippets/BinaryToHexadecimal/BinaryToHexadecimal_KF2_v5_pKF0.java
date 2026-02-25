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
        int remainingBinary = binary;

        while (remainingBinary != 0) {
            int nibbleValue = extractNextNibbleAsDecimal(remainingBinary);
            hexResult.insert(0, toHexDigit(nibbleValue));
            remainingBinary = removeProcessedBits(remainingBinary);
        }

        return hexResult.toString();
    }

    private static int extractNextNibbleAsDecimal(int binary) {
        int nibbleValue = 0;
        int bitPosition = 0;
        int remainingBinary = binary;

        while (bitPosition < BITS_IN_HEX_DIGIT && remainingBinary != 0) {
            int currentBit = remainingBinary % DECIMAL_BASE;
            validateBinaryDigit(currentBit);

            nibbleValue |= currentBit << bitPosition;
            remainingBinary /= DECIMAL_BASE;
            bitPosition++;
        }

        return nibbleValue;
    }

    private static int removeProcessedBits(int binary) {
        int bitsProcessed = 0;
        int remainingBinary = binary;

        while (bitsProcessed < BITS_IN_HEX_DIGIT && remainingBinary != 0) {
            remainingBinary /= DECIMAL_BASE;
            bitsProcessed++;
        }

        return remainingBinary;
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