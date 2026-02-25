package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

/**
 * Utility class for binary addition using character-based operations.
 */
public class BinaryAdder {

    /**
     * Computes the sum bit of a full-adder for three binary inputs.
     *
     * @param a       first binary digit ('0' or '1')
     * @param b       second binary digit ('0' or '1')
     * @param carryIn carry-in binary digit ('0' or '1')
     * @return the sum bit ('0' or '1')
     */
    public char computeSumBit(char a, char b, char carryIn) {
        int onesCount = countOnes(a, b, carryIn);
        return (onesCount % 2 == 0) ? '0' : '1';
    }

    /**
     * Computes the carry-out bit of a full-adder for three binary inputs.
     *
     * @param a       first binary digit ('0' or '1')
     * @param b       second binary digit ('0' or '1')
     * @param carryIn carry-in binary digit ('0' or '1')
     * @return the carry-out bit ('0' or '1')
     */
    public char computeCarryBit(char a, char b, char carryIn) {
        int onesCount = countOnes(a, b, carryIn);
        return (onesCount >= 2) ? '1' : '0';
    }

    /**
     * Adds two binary numbers represented as strings.
     *
     * @param firstBinary  first binary number as a string
     * @param secondBinary second binary number as a string
     * @return the binary sum of the two inputs as a string
     */
    public String addBinaryStrings(String firstBinary, String secondBinary) {
        int maxLength = Math.max(firstBinary.length(), secondBinary.length());

        String paddedFirst = leftPadWithZeros(firstBinary, maxLength);
        String paddedSecond = leftPadWithZeros(secondBinary, maxLength);

        StringBuilder result = new StringBuilder();
        char carry = '0';

        for (int i = maxLength - 1; i >= 0; i--) {
            char a = paddedFirst.charAt(i);
            char b = paddedSecond.charAt(i);

            char sumBit = computeSumBit(a, b, carry);
            carry = computeCarryBit(a, b, carry);

            result.append(sumBit);
        }

        if (carry == '1') {
            result.append('1');
        }

        return result.reverse().toString();
    }

    /**
     * Counts the number of '1' bits in the given array of bits.
     *
     * @param bits variable number of binary digits ('0' or '1')
     * @return the count of '1' bits
     */
    private int countOnes(char... bits) {
        int onesCount = 0;
        for (char bit : bits) {
            if (bit == '1') {
                onesCount++;
            }
        }
        return onesCount;
    }

    /**
     * Left-pads a binary string with zeros to reach the specified length.
     *
     * @param binary       binary string to pad
     * @param targetLength desired total length after padding
     * @return the padded binary string
     */
    private String leftPadWithZeros(String binary, int targetLength) {
        int paddingLength = targetLength - binary.length();
        if (paddingLength <= 0) {
            return binary;
        }
        String padding = String.join("", Collections.nCopies(paddingLength, "0"));
        return padding + binary;
    }
}