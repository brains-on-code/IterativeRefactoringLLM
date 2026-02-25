package com.thealgorithms.greedyalgorithms;

/**
 * Performs binary addition on strings containing '0' and '1'.
 */
public class BinaryAddition {

    private static final char ONE = '1';
    private static final char ZERO = '0';

    /**
     * Returns the sum bit of a full-adder for inputs (a, b, carryIn).
     */
    public char sum(char a, char b, char carryIn) {
        int onesCount = countOnes(a, b, carryIn);
        return (onesCount % 2 == 0) ? ZERO : ONE;
    }

    /**
     * Returns the carry-out bit of a full-adder for inputs (a, b, carryIn).
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
     * Left-pads the given binary string with '0' to reach the target length.
     */
    private String padWithLeadingZeros(String s, int targetLength) {
        int zerosToAdd = targetLength - s.length();
        if (zerosToAdd <= 0) {
            return s;
        }

        StringBuilder padded = new StringBuilder(targetLength);
        for (int i = 0; i < zerosToAdd; i++) {
            padded.append(ZERO);
        }

        return padded.append(s).toString();
    }
}