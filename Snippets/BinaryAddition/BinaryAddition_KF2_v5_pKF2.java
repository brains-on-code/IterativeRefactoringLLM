package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

public class BinaryAddition {

    private static final char ONE = '1';
    private static final char ZERO = '0';

    /**
     * Counts how many of the three bits are '1'.
     */
    private int countOnes(char firstBit, char secondBit, char carryBit) {
        int count = 0;
        if (firstBit == ONE) count++;
        if (secondBit == ONE) count++;
        if (carryBit == ONE) count++;
        return count;
    }

    /**
     * Computes the sum bit for a full adder (A + B + carryIn).
     */
    public char sum(char firstBit, char secondBit, char carryBit) {
        int onesCount = countOnes(firstBit, secondBit, carryBit);
        return (onesCount % 2 == 0) ? ZERO : ONE;
    }

    /**
     * Computes the carry-out bit for a full adder (A + B + carryIn).
     */
    public char carry(char firstBit, char secondBit, char carryBit) {
        int onesCount = countOnes(firstBit, secondBit, carryBit);
        return (onesCount >= 2) ? ONE : ZERO;
    }

    /**
     * Left-pads the given binary string with zeros until it reaches targetLength.
     */
    private String padWithLeadingZeros(String value, int targetLength) {
        int zerosToAdd = targetLength - value.length();
        if (zerosToAdd <= 0) {
            return value;
        }
        String padding = String.join("", Collections.nCopies(zerosToAdd, "0"));
        return padding + value;
    }

    /**
     * Adds two binary numbers represented as strings and returns the result as a binary string.
     */
    public String addBinary(String firstBinary, String secondBinary) {
        int maxLength = Math.max(firstBinary.length(), secondBinary.length());
        String paddedFirst = padWithLeadingZeros(firstBinary, maxLength);
        String paddedSecond = padWithLeadingZeros(secondBinary, maxLength);

        StringBuilder result = new StringBuilder();
        char carryBit = ZERO;

        for (int i = maxLength - 1; i >= 0; i--) {
            char bitA = paddedFirst.charAt(i);
            char bitB = paddedSecond.charAt(i);

            char sumBit = sum(bitA, bitB, carryBit);
            carryBit = carry(bitA, bitB, carryBit);

            result.append(sumBit);
        }

        if (carryBit == ONE) {
            result.append(ONE);
        }

        return result.reverse().toString();
    }
}