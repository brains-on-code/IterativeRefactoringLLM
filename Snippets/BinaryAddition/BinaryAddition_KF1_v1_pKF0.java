package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

/**
 * Utility class for binary string addition using bitwise operations.
 */
public class Class1 {

    /**
     * Computes the sum bit of three binary characters ('0' or '1').
     *
     * @param bitA first input bit
     * @param bitB second input bit
     * @param carryIn carry-in bit
     * @return resulting sum bit ('0' or '1')
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
     * Computes the carry-out bit of three binary characters ('0' or '1').
     *
     * @param bitA first input bit
     * @param bitB second input bit
     * @param carryIn carry-in bit
     * @return resulting carry-out bit ('0' or '1')
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
     * @param firstBinary  first binary string
     * @param secondBinary second binary string
     * @return binary string representing the sum of the inputs
     */
    public String addBinaryStrings(String firstBinary, String secondBinary) {
        int maxLength = Math.max(firstBinary.length(), secondBinary.length());

        firstBinary =
                String.join("", Collections.nCopies(maxLength - firstBinary.length(), "0")) + firstBinary;
        secondBinary =
                String.join("", Collections.nCopies(maxLength - secondBinary.length(), "0")) + secondBinary;

        StringBuilder result = new StringBuilder();
        char carry = '0';

        for (int i = maxLength - 1; i >= 0; i--) {
            char sumBit = computeSumBit(firstBinary.charAt(i), secondBinary.charAt(i), carry);
            carry = computeCarryBit(firstBinary.charAt(i), secondBinary.charAt(i), carry);
            result.append(sumBit);
        }

        if (carry == '1') {
            result.append('1');
        }

        return result.reverse().toString();
    }
}