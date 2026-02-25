package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

public class BinaryAddition {

    private char computeSumBit(char firstBit, char secondBit, char carryIn) {
        int onesCount = 0;
        if (firstBit == '1') {
            onesCount++;
        }
        if (secondBit == '1') {
            onesCount++;
        }
        if (carryIn == '1') {
            onesCount++;
        }
        return onesCount % 2 == 0 ? '0' : '1';
    }

    private char computeCarryBit(char firstBit, char secondBit, char carryIn) {
        int onesCount = 0;
        if (firstBit == '1') {
            onesCount++;
        }
        if (secondBit == '1') {
            onesCount++;
        }
        if (carryIn == '1') {
            onesCount++;
        }
        return onesCount >= 2 ? '1' : '0';
    }

    public String addBinary(String firstBinary, String secondBinary) {
        int maxLength = Math.max(firstBinary.length(), secondBinary.length());

        String paddedFirstBinary =
                String.join("", Collections.nCopies(maxLength - firstBinary.length(), "0")) + firstBinary;
        String paddedSecondBinary =
                String.join("", Collections.nCopies(maxLength - secondBinary.length(), "0")) + secondBinary;

        StringBuilder sumBuilder = new StringBuilder();
        char carry = '0';

        for (int index = maxLength - 1; index >= 0; index--) {
            char firstBit = paddedFirstBinary.charAt(index);
            char secondBit = paddedSecondBinary.charAt(index);

            char sumBit = computeSumBit(firstBit, secondBit, carry);
            carry = computeCarryBit(firstBit, secondBit, carry);

            sumBuilder.append(sumBit);
        }

        if (carry == '1') {
            sumBuilder.append('1');
        }

        return sumBuilder.reverse().toString();
    }
}