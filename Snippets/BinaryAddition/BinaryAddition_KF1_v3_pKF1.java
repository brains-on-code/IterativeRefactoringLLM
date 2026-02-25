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
     * @param firstInputBit first input bit ('0' or '1')
     * @param secondInputBit second input bit ('0' or '1')
     * @param carryInBit carry-in bit ('0' or '1')
     * @return sum bit ('0' or '1')
     */
    public char computeSumBit(char firstInputBit, char secondInputBit, char carryInBit) {
        int onesCount = 0;

        if (firstInputBit == BIT_ONE) {
            onesCount++;
        }
        if (secondInputBit == BIT_ONE) {
            onesCount++;
        }
        if (carryInBit == BIT_ONE) {
            onesCount++;
        }

        return onesCount % 2 == 0 ? BIT_ZERO : BIT_ONE;
    }

    /**
     * Computes the carry-out bit of a full adder for three binary inputs.
     *
     * @param firstInputBit first input bit ('0' or '1')
     * @param secondInputBit second input bit ('0' or '1')
     * @param carryInBit carry-in bit ('0' or '1')
     * @return carry-out bit ('0' or '1')
     */
    public char computeCarryBit(char firstInputBit, char secondInputBit, char carryInBit) {
        int onesCount = 0;

        if (firstInputBit == BIT_ONE) {
            onesCount++;
        }
        if (secondInputBit == BIT_ONE) {
            onesCount++;
        }
        if (carryInBit == BIT_ONE) {
            onesCount++;
        }

        return onesCount >= 2 ? BIT_ONE : BIT_ZERO;
    }

    /**
     * Adds two binary numbers represented as strings.
     *
     * @param firstBinaryString first binary number
     * @param secondBinaryString second binary number
     * @return binary sum of the two input numbers
     */
    public String addBinaryStrings(String firstBinaryString, String secondBinaryString) {
        int maxLength = Math.max(firstBinaryString.length(), secondBinaryString.length());

        firstBinaryString =
                String.join("", Collections.nCopies(maxLength - firstBinaryString.length(), "0")) + firstBinaryString;
        secondBinaryString =
                String.join("", Collections.nCopies(maxLength - secondBinaryString.length(), "0")) + secondBinaryString;

        StringBuilder resultBuilder = new StringBuilder();
        char carryBit = BIT_ZERO;

        for (int index = maxLength - 1; index >= 0; index--) {
            char firstInputBit = firstBinaryString.charAt(index);
            char secondInputBit = secondBinaryString.charAt(index);

            char sumBit = computeSumBit(firstInputBit, secondInputBit, carryBit);
            carryBit = computeCarryBit(firstInputBit, secondInputBit, carryBit);

            resultBuilder.append(sumBit);
        }

        if (carryBit == BIT_ONE) {
            resultBuilder.append(BIT_ONE);
        }

        return resultBuilder.reverse().toString();
    }
}