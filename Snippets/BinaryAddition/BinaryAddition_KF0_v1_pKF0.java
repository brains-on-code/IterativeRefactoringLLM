package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

/**
 * BinaryAddition class to perform binary addition of two binary strings.
 */
public class BinaryAddition {

    private static final char ONE = '1';
    private static final char ZERO = '0';

    /**
     * Computes the sum of two binary characters and a carry.
     *
     * @param a     First binary character ('0' or '1').
     * @param b     Second binary character ('0' or '1').
     * @param carry The carry from the previous operation ('0' or '1').
     * @return The sum as a binary character ('0' or '1').
     */
    public char sum(char a, char b, char carry) {
        int onesCount = countOnes(a, b, carry);
        return (onesCount % 2 == 0) ? ZERO : ONE;
    }

    /**
     * Computes the carry for the next higher bit from two binary characters and a carry.
     *
     * @param a     First binary character ('0' or '1').
     * @param b     Second binary character ('0' or '1').
     * @param carry The carry from the previous operation ('0' or '1').
     * @return The carry for the next bit ('0' or '1').
     */
    public char carry(char a, char b, char carry) {
        int onesCount = countOnes(a, b, carry);
        return (onesCount >= 2) ? ONE : ZERO;
    }

    /**
     * Adds two binary strings and returns their sum as a binary string.
     *
     * @param a First binary string.
     * @param b Second binary string.
     * @return Binary string representing the sum of the two binary inputs.
     */
    public String addBinary(String a, String b) {
        int maxLength = Math.max(a.length(), b.length());

        String paddedA = padWithLeadingZeros(a, maxLength);
        String paddedB = padWithLeadingZeros(b, maxLength);

        StringBuilder result = new StringBuilder(maxLength + 1);
        char carry = ZERO;

        for (int i = maxLength - 1; i >= 0; i--) {
            char bitA = paddedA.charAt(i);
            char bitB = paddedB.charAt(i);

            char sumBit = sum(bitA, bitB, carry);
            carry = carry(bitA, bitB, carry);

            result.append(sumBit);
        }

        if (carry == ONE) {
            result.append(ONE);
        }

        return result.reverse().toString();
    }

    private int countOnes(char... bits) {
        int count = 0;
        for (char bit : bits) {
            if (bit == ONE) {
                count++;
            }
        }
        return count;
    }

    private String padWithLeadingZeros(String value, int targetLength) {
        int zerosToAdd = targetLength - value.length();
        if (zerosToAdd <= 0) {
            return value;
        }
        String prefix = String.join("", Collections.nCopies(zerosToAdd, "0"));
        return prefix + value;
    }
}