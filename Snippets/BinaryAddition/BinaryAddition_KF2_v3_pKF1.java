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

    public String addBinary(String binaryA, String binaryB) {
        int maxLength = Math.max(binaryA.length(), binaryB.length());

        String paddedBinaryA =
                String.join("", Collections.nCopies(maxLength - binaryA.length(), "0")) + binaryA;
        String paddedBinaryB =
                String.join("", Collections.nCopies(maxLength - binaryB.length(), "0")) + binaryB;

        StringBuilder result = new StringBuilder();
        char carry = '0';

        for (int position = maxLength - 1; position >= 0; position--) {
            char bitA = paddedBinaryA.charAt(position);
            char bitB = paddedBinaryB.charAt(position);

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