package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

/**
 * Utility class for binary addition using character-based operations.
 */
public class BinaryAdder {

    /**
     * Computes the sum bit of a full adder for three binary inputs.
     *
     * @param bitA first input bit ('0' or '1')
     * @param bitB second input bit ('0' or '1')
     * @param carryIn carry-in bit ('0' or '1')
     * @return sum bit ('0' or '1')
     */
    public char computeSumBit(char bitA, char bitB, char carryIn) {
        int onesCount = 0;
        if (bitA == '1') {
            onesCount++;
        }
        if (bitB == '1') {
            onesCount++;
        }
        if (carryIn == '1') {
            onesCount++;
        }
        return onesCount % 2 == 0 ? '0' : '1';
    }

    /**
     * Computes the carry-out bit of a full adder for three binary inputs.
     *
     * @param bitA first input bit ('0' or '1')
     * @param bitB second input bit ('0' or '1')
     * @param carryIn carry-in bit ('0' or '1')
     * @return carry-out bit ('0' or '1')
     */
    public char computeCarryBit(char bitA, char bitB, char carryIn) {
        int onesCount = 0;
        if (bitA == '1') {
            onesCount++;
        }
        if (bitB == '1') {
            onesCount++;
        }
        if (carryIn == '1') {
            onesCount++;
        }
        return onesCount >= 2 ? '1' : '0';
    }

    /**
     * Adds two binary numbers represented as strings.
     *
     * @param binaryA first binary number
     * @param binaryB second binary number
     * @return binary sum of the two input numbers
     */
    public String addBinaryStrings(String binaryA, String binaryB) {
        int maxLength = Math.max(binaryA.length(), binaryB.length());

        binaryA = String.join("", Collections.nCopies(maxLength - binaryA.length(), "0")) + binaryA;
        binaryB = String.join("", Collections.nCopies(maxLength - binaryB.length(), "0")) + binaryB;

        StringBuilder resultBuilder = new StringBuilder();
        char carry = '0';

        for (int index = maxLength - 1; index >= 0; index--) {
            char sumBit = computeSumBit(binaryA.charAt(index), binaryB.charAt(index), carry);
            carry = computeCarryBit(binaryA.charAt(index), binaryB.charAt(index), carry);
            resultBuilder.append(sumBit);
        }

        if (carry == '1') {
            resultBuilder.append('1');
        }

        return resultBuilder.reverse().toString();
    }
}