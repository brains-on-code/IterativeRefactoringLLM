package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

/**
 * BinaryAddition class to perform binary addition of two binary strings.
 */
public class BinaryAddition {

    /**
     * Computes the sum bit of two binary bits and an incoming carry bit.
     *
     * @param firstBit   First binary character ('0' or '1').
     * @param secondBit  Second binary character ('0' or '1').
     * @param carryIn    The carry from the previous operation ('0' or '1').
     * @return The sum bit as a binary character ('0' or '1').
     */
    public char computeSumBit(char firstBit, char secondBit, char carryIn) {
        int numberOfOnes = 0;
        if (firstBit == '1') {
            numberOfOnes++;
        }
        if (secondBit == '1') {
            numberOfOnes++;
        }
        if (carryIn == '1') {
            numberOfOnes++;
        }
        return numberOfOnes % 2 == 0 ? '0' : '1';
    }

    /**
     * Computes the carry-out bit for the next higher position from two binary bits and an incoming carry bit.
     *
     * @param firstBit   First binary character ('0' or '1').
     * @param secondBit  Second binary character ('0' or '1').
     * @param carryIn    The carry from the previous operation ('0' or '1').
     * @return The carry-out bit for the next position ('0' or '1').
     */
    public char computeCarryBit(char firstBit, char secondBit, char carryIn) {
        int numberOfOnes = 0;
        if (firstBit == '1') {
            numberOfOnes++;
        }
        if (secondBit == '1') {
            numberOfOnes++;
        }
        if (carryIn == '1') {
            numberOfOnes++;
        }
        return numberOfOnes >= 2 ? '1' : '0';
    }

    /**
     * Adds two binary strings and returns their sum as a binary string.
     *
     * @param firstBinaryString  First binary string.
     * @param secondBinaryString Second binary string.
     * @return Binary string representing the sum of the two binary inputs.
     */
    public String addBinary(String firstBinaryString, String secondBinaryString) {
        int maxLength = Math.max(firstBinaryString.length(), secondBinaryString.length());

        String paddedFirstBinary =
                String.join("", Collections.nCopies(maxLength - firstBinaryString.length(), "0"))
                        + firstBinaryString;
        String paddedSecondBinary =
                String.join("", Collections.nCopies(maxLength - secondBinaryString.length(), "0"))
                        + secondBinaryString;

        StringBuilder resultBuilder = new StringBuilder();
        char carry = '0';

        for (int currentIndex = maxLength - 1; currentIndex >= 0; currentIndex--) {
            char firstBit = paddedFirstBinary.charAt(currentIndex);
            char secondBit = paddedSecondBinary.charAt(currentIndex);

            char sumBit = computeSumBit(firstBit, secondBit, carry);
            carry = computeCarryBit(firstBit, secondBit, carry);

            resultBuilder.append(sumBit);
        }

        if (carry == '1') {
            resultBuilder.append('1');
        }

        return resultBuilder.reverse().toString();
    }
}