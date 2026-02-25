package com.thealgorithms.greedyalgorithms;

/**
 * Utility class for binary string addition using bitwise-like operations.
 */
public class Class1 {

    private static final char ONE = '1';
    private static final char ZERO = '0';

    /**
     * Computes the sum bit of three binary characters ('0' or '1').
     *
     * @param bitA    first input bit
     * @param bitB    second input bit
     * @param carryIn carry-in bit
     * @return resulting sum bit ('0' or '1')
     */
    public char computeSumBit(char bitA, char bitB, char carryIn) {
        int onesCount = countOnes(bitA, bitB, carryIn);
        return isEven(onesCount) ? ZERO : ONE;
    }

    /**
     * Computes the carry-out bit of three binary characters ('0' or '1').
     *
     * @param bitA    first input bit
     * @param bitB    second input bit
     * @param carryIn carry-in bit
     * @return resulting carry-out bit ('0' or '1')
     */
    public char computeCarryBit(char bitA, char bitB, char carryIn) {
        int onesCount = countOnes(bitA, bitB, carryIn);
        return onesCount >= 2 ? ONE : ZERO;
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

    private boolean isEven(int number) {
        return (number & 1) == 0;
    }

    /**
     * Adds two binary strings and returns their sum as a binary string.
     *
     * @param firstBinary  first binary string
     * @param secondBinary second binary string
     * @return binary string representing the sum of the inputs
     */
    public String addBinaryStrings(String firstBinary, String secondBinary) {
        int maxLength = Math.max(firstBinary.length(), secondBinary.length());

        String paddedFirst = padWithLeadingZeros(firstBinary, maxLength);
        String paddedSecond = padWithLeadingZeros(secondBinary, maxLength);

        StringBuilder result = new StringBuilder(maxLength + 1);
        char carry = ZERO;

        for (int i = maxLength - 1; i >= 0; i--) {
            char bitA = paddedFirst.charAt(i);
            char bitB = paddedSecond.charAt(i);

            char sumBit = computeSumBit(bitA, bitB, carry);
            carry = computeCarryBit(bitA, bitB, carry);

            result.append(sumBit);
        }

        if (carry == ONE) {
            result.append(ONE);
        }

        return result.reverse().toString();
    }

    private String padWithLeadingZeros(String binary, int targetLength) {
        int zerosToAdd = targetLength - binary.length();
        if (zerosToAdd <= 0) {
            return binary;
        }

        StringBuilder padded = new StringBuilder(targetLength);
        for (int i = 0; i < zerosToAdd; i++) {
            padded.append(ZERO);
        }
        padded.append(binary);
        return padded.toString();
    }
}