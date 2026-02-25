package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

/**
 * Utility class for binary addition using character-based operations.
 */
public class BinaryAdder {

    private static final char ONE_BIT = '1';
    private static final char ZERO_BIT = '0';

    /**
     * Computes the sum bit of a full adder for three binary inputs.
     *
     * @param firstBit first input bit ('0' or '1')
     * @param secondBit second input bit ('0' or '1')
     * @param carryIn carry-in bit ('0' or '1')
     * @return sum bit ('0' or '1')
     */
    public char computeSumBit(char firstBit, char secondBit, char carryIn) {
        int oneBitsCount = 0;

        if (firstBit == ONE_BIT) {
            oneBitsCount++;
        }
        if (secondBit == ONE_BIT) {
            oneBitsCount++;
        }
        if (carryIn == ONE_BIT) {
            oneBitsCount++;
        }

        return oneBitsCount % 2 == 0 ? ZERO_BIT : ONE_BIT;
    }

    /**
     * Computes the carry-out bit of a full adder for three binary inputs.
     *
     * @param firstBit first input bit ('0' or '1')
     * @param secondBit second input bit ('0' or '1')
     * @param carryIn carry-in bit ('0' or '1')
     * @return carry-out bit ('0' or '1')
     */
    public char computeCarryBit(char firstBit, char secondBit, char carryIn) {
        int oneBitsCount = 0;

        if (firstBit == ONE_BIT) {
            oneBitsCount++;
        }
        if (secondBit == ONE_BIT) {
            oneBitsCount++;
        }
        if (carryIn == ONE_BIT) {
            oneBitsCount++;
        }

        return oneBitsCount >= 2 ? ONE_BIT : ZERO_BIT;
    }

    /**
     * Adds two binary numbers represented as strings.
     *
     * @param firstBinary first binary number
     * @param secondBinary second binary number
     * @return binary sum of the two input numbers
     */
    public String addBinaryStrings(String firstBinary, String secondBinary) {
        int maxLength = Math.max(firstBinary.length(), secondBinary.length());

        firstBinary =
                String.join("", Collections.nCopies(maxLength - firstBinary.length(), "0")) + firstBinary;
        secondBinary =
                String.join("", Collections.nCopies(maxLength - secondBinary.length(), "0")) + secondBinary;

        StringBuilder sumBuilder = new StringBuilder();
        char carry = ZERO_BIT;

        for (int position = maxLength - 1; position >= 0; position--) {
            char firstBit = firstBinary.charAt(position);
            char secondBit = secondBinary.charAt(position);

            char sumBit = computeSumBit(firstBit, secondBit, carry);
            carry = computeCarryBit(firstBit, secondBit, carry);

            sumBuilder.append(sumBit);
        }

        if (carry == ONE_BIT) {
            sumBuilder.append(ONE_BIT);
        }

        return sumBuilder.reverse().toString();
    }
}