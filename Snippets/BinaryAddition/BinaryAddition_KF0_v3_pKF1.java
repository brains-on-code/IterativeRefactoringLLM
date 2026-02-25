package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

/**
 * BinaryAddition class to perform binary addition of two binary strings.
 */
public class BinaryAddition {

    /**
     * Computes the sum bit of two binary bits and an incoming carry bit.
     *
     * @param leftBit   First binary character ('0' or '1').
     * @param rightBit  Second binary character ('0' or '1').
     * @param carryIn   The carry from the previous operation ('0' or '1').
     * @return The sum bit as a binary character ('0' or '1').
     */
    public char computeSumBit(char leftBit, char rightBit, char carryIn) {
        int onesCount = 0;
        if (leftBit == '1') {
            onesCount++;
        }
        if (rightBit == '1') {
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
     * @param leftBit   First binary character ('0' or '1').
     * @param rightBit  Second binary character ('0' or '1').
     * @param carryIn   The carry from the previous operation ('0' or '1').
     * @return The carry-out bit for the next position ('0' or '1').
     */
    public char computeCarryBit(char leftBit, char rightBit, char carryIn) {
        int onesCount = 0;
        if (leftBit == '1') {
            onesCount++;
        }
        if (rightBit == '1') {
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
     * @param leftBinaryString   First binary string.
     * @param rightBinaryString  Second binary string.
     * @return Binary string representing the sum of the two binary inputs.
     */
    public String addBinary(String leftBinaryString, String rightBinaryString) {
        int maxLength = Math.max(leftBinaryString.length(), rightBinaryString.length());

        String paddedLeftBinary =
                String.join("", Collections.nCopies(maxLength - leftBinaryString.length(), "0"))
                        + leftBinaryString;
        String paddedRightBinary =
                String.join("", Collections.nCopies(maxLength - rightBinaryString.length(), "0"))
                        + rightBinaryString;

        StringBuilder sumBuilder = new StringBuilder();
        char carry = '0';

        for (int index = maxLength - 1; index >= 0; index--) {
            char leftBit = paddedLeftBinary.charAt(index);
            char rightBit = paddedRightBinary.charAt(index);

            char sumBit = computeSumBit(leftBit, rightBit, carry);
            carry = computeCarryBit(leftBit, rightBit, carry);

            sumBuilder.append(sumBit);
        }

        if (carry == '1') {
            sumBuilder.append('1');
        }

        return sumBuilder.reverse().toString();
    }
}