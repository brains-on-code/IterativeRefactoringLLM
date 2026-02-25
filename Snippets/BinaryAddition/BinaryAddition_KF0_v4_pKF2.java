package com.thealgorithms.greedyalgorithms;

/**
 * Utility class for binary addition on binary strings.
 */
public class BinaryAddition {

    private static final char ONE = '1';
    private static final char ZERO = '0';

    /**
     * Computes the sum bit of three binary bits (a, b, carryIn).
     */
    public char sum(char a, char b, char carryIn) {
        int onesCount = countOnes(a, b, carryIn);
        return (onesCount % 2 == 0) ? ZERO : ONE;
    }

    /**
     * Computes the carry-out bit of three binary bits (a, b, carryIn).
     */
    public char carry(char a, char b, char carryIn) {
        int onesCount = countOnes(a, b, carryIn);
        return (onesCount >= 2) ? ONE : ZERO;
    }

    /**
     * Adds two binary strings and returns their sum as a binary string.
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

    /**
     * Counts how many of the given bits are '1'.
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

    /**
     * Pads the given binary string with leading zeros to reach the target length.
     */
    private String padWithLeadingZeros(String s, int targetLength) {
        int zerosToAdd = targetLength - s.length();
        if (zerosToAdd <= 0) {
            return s;
        }

        StringBuilder padding = new StringBuilder(zerosToAdd + s.length());
        for (int i = 0; i < zerosToAdd; i++) {
            padding.append(ZERO);
        }

        return padding.append(s).toString();
    }
}