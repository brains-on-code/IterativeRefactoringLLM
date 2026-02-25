package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

/**
 * BinaryAddition class to perform binary addition of two binary strings.
 */
public class BinaryAddition {

    /**
     * Computes the sum bit of two binary bits and an incoming carry bit.
     *
     * @param bitA   First binary character ('0' or '1').
     * @param bitB   Second binary character ('0' or '1').
     * @param carryIn The carry from the previous operation ('0' or '1').
     * @return The sum bit as a binary character ('0' or '1').
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
     * Computes the carry-out bit for the next higher position from two binary bits and an incoming carry bit.
     *
     * @param bitA    First binary character ('0' or '1').
     * @param bitB    Second binary character ('0' or '1').
     * @param carryIn The carry from the previous operation ('0' or '1').
     * @return The carry-out bit for the next position ('0' or '1').
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
     * Adds two binary strings and returns their sum as a binary string.
     *
     * @param binaryA First binary string.
     * @param binaryB Second binary string.
     * @return Binary string representing the sum of the two binary inputs.
     */
    public String addBinary(String binaryA, String binaryB) {
        int maxLength = Math.max(binaryA.length(), binaryB.length());

        String paddedA =
                String.join("", Collections.nCopies(maxLength - binaryA.length(), "0")) + binaryA;
        String paddedB =
                String.join("", Collections.nCopies(maxLength - binaryB.length(), "0")) + binaryB;

        StringBuilder sumBuilder = new StringBuilder();
        char carry = '0';

        for (int index = maxLength - 1; index >= 0; index--) {
            char bitA = paddedA.charAt(index);
            char bitB = paddedB.charAt(index);

            char sumBit = computeSumBit(bitA, bitB, carry);
            carry = computeCarryBit(bitA, bitB, carry);

            sumBuilder.append(sumBit);
        }

        if (carry == '1') {
            sumBuilder.append('1');
        }

        return sumBuilder.reverse().toString();
    }
}