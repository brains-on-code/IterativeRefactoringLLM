package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

public class BinaryAddition {

    private char calculateSumBit(char bitA, char bitB, char carryIn) {
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

    private char calculateCarryBit(char bitA, char bitB, char carryIn) {
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

    public String addBinary(String firstBinary, String secondBinary) {
        int maxLength = Math.max(firstBinary.length(), secondBinary.length());

        firstBinary =
                String.join("", Collections.nCopies(maxLength - firstBinary.length(), "0")) + firstBinary;
        secondBinary =
                String.join("", Collections.nCopies(maxLength - secondBinary.length(), "0")) + secondBinary;

        StringBuilder result = new StringBuilder();
        char carry = '0';

        for (int index = maxLength - 1; index >= 0; index--) {
            char bitA = firstBinary.charAt(index);
            char bitB = secondBinary.charAt(index);

            char sumBit = calculateSumBit(bitA, bitB, carry);
            carry = calculateCarryBit(bitA, bitB, carry);

            result.append(sumBit);
        }

        if (carry == '1') {
            result.append('1');
        }

        return result.reverse().toString();
    }
}