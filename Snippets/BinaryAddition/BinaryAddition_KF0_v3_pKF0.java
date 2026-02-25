package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

/**
 * Performs binary addition of two binary strings.
 */
public class BinaryAddition {

    private static final char ONE = '1';
    private static final char ZERO = '0';

    /**
     * Computes the sum bit of two binary characters and a carry.
     *
     * @param a     first binary character ('0' or '1')
     * @param b     second binary character ('0' or '1')
     * @param carry carry from the previous operation ('0' or '1')
     * @return sum bit as a binary character ('0' or '1')
     */
    public char sum(char a, char b, char carry) {
        int onesCount = countOnes(a, b, carry);
        return isEven(onesCount) ? ZERO : ONE;
    }

    /**
     * Computes the carry bit for the next higher position.
     *
     * @param a     first binary character ('0' or '1')
     * @param b     second binary character ('0' or '1')
     * @param carry carry from the previous operation ('0' or '1')
     * @return carry bit for the next position ('0' or '1')
     */
    public char carry(char a, char b, char carry) {
        int onesCount = countOnes(a, b, carry);
        return onesCount >= 2 ? ONE : ZERO;
    }

    /**
     * Adds two binary strings and returns their sum as a binary string.
     *
     * @param a first binary string
     * @param b second binary string
     * @return binary string representing the sum of the two inputs
     */
    public String addBinary(String a, String b) {
        int maxLength = Math.max(a.length(), b.length());

        String paddedA = padWithLeadingZeros(a, maxLength);
        String paddedB = padWithLeadingZeros(b, maxLength);

        StringBuilder result = new StringBuilder(maxLength + 1);
        char carryBit = ZERO;

        for (int i = maxLength - 1; i >= 0; i--) {
            char bitA = paddedA.charAt(i);
            char bitB = paddedB.charAt(i);

            char sumBit = sum(bitA, bitB, carryBit);
            carryBit = carry(bitA, bitB, carryBit);

            result.append(sumBit);
        }

        if (carryBit == ONE) {
            result.append(ONE);
        }

        return result.reverse().toString();
    }

    /**
     * Counts how many of the provided bits are '1'.
     *
     * @param bits variable number of binary characters
     * @return number of bits that are '1'
     */
    private int countOnes(char... bits) {
        int count = 0;
        for (char bit : bits) {
            if (bit == ONE) {
                count++;
            }
        }
        return count;
    }

    private boolean isEven(int value) {
        return (value & 1) == 0;
    }

    /**
     * Pads the given binary string with leading zeros to reach the target length.
     *
     * @param value        original binary string
     * @param targetLength desired length after padding
     * @return padded binary string
     */
    private String padWithLeadingZeros(String value, int targetLength) {
        int zerosToAdd = targetLength - value.length();
        if (zerosToAdd <= 0) {
            return value;
        }
        String prefix = String.join("", Collections.nCopies(zerosToAdd, "0"));
        return prefix + value;
    }
}