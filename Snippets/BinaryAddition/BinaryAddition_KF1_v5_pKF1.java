package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

/**
 * Utility class for binary addition using character-based operations.
 */
public class BinaryAdder {

    private static final char BIT_ONE = '1';
    private static final char BIT_ZERO = '0';

    /**
     * Computes the sum bit of a full adder for three binary inputs.
     *
     * @param augendBit first input bit ('0' or '1')
     * @param addendBit second input bit ('0' or '1')
     * @param carryIn carry-in bit ('0' or '1')
     * @return sum bit ('0' or '1')
     */
    public char computeSumBit(char augendBit, char addendBit, char carryIn) {
        int setBitCount = countSetBits(augendBit, addendBit, carryIn);
        return setBitCount % 2 == 0 ? BIT_ZERO : BIT_ONE;
    }

    /**
     * Computes the carry-out bit of a full adder for three binary inputs.
     *
     * @param augendBit first input bit ('0' or '1')
     * @param addendBit second input bit ('0' or '1')
     * @param carryIn carry-in bit ('0' or '1')
     * @return carry-out bit ('0' or '1')
     */
    public char computeCarryBit(char augendBit, char addendBit, char carryIn) {
        int setBitCount = countSetBits(augendBit, addendBit, carryIn);
        return setBitCount >= 2 ? BIT_ONE : BIT_ZERO;
    }

    private int countSetBits(char... bits) {
        int setBitCount = 0;
        for (char bit : bits) {
            if (bit == BIT_ONE) {
                setBitCount++;
            }
        }
        return setBitCount;
    }

    /**
     * Adds two binary numbers represented as strings.
     *
     * @param augendBinary first binary number
     * @param addendBinary second binary number
     * @return binary sum of the two input numbers
     */
    public String addBinaryStrings(String augendBinary, String addendBinary) {
        int maxLength = Math.max(augendBinary.length(), addendBinary.length());

        augendBinary =
                String.join("", Collections.nCopies(maxLength - augendBinary.length(), "0")) + augendBinary;
        addendBinary =
                String.join("", Collections.nCopies(maxLength - addendBinary.length(), "0")) + addendBinary;

        StringBuilder sumBitsBuilder = new StringBuilder();
        char carryBit = BIT_ZERO;

        for (int index = maxLength - 1; index >= 0; index--) {
            char augendBit = augendBinary.charAt(index);
            char addendBit = addendBinary.charAt(index);

            char sumBit = computeSumBit(augendBit, addendBit, carryBit);
            carryBit = computeCarryBit(augendBit, addendBit, carryBit);

            sumBitsBuilder.append(sumBit);
        }

        if (carryBit == BIT_ONE) {
            sumBitsBuilder.append(BIT_ONE);
        }

        return sumBitsBuilder.reverse().toString();
    }
}