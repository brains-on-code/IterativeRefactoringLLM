package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

public class BinaryAddition {

    private static final char ONE = '1';
    private static final char ZERO = '0';

    private int countOnes(char... bits) {
        int count = 0;
        for (char bit : bits) {
            if (bit == ONE) {
                count++;
            }
        }
        return count;
    }

    private char calculateSumBit(char a, char b, char carry) {
        return (countOnes(a, b, carry) % 2 == 0) ? ZERO : ONE;
    }

    private char calculateCarryBit(char a, char b, char carry) {
        return (countOnes(a, b, carry) >= 2) ? ONE : ZERO;
    }

    private String padWithLeadingZeros(String value, int targetLength) {
        int paddingLength = targetLength - value.length();
        if (paddingLength <= 0) {
            return value;
        }
        String padding = String.join("", Collections.nCopies(paddingLength, "0"));
        return padding + value;
    }

    public String addBinary(String firstBinary, String secondBinary) {
        int maxLength = Math.max(firstBinary.length(), secondBinary.length());
        String paddedFirst = padWithLeadingZeros(firstBinary, maxLength);
        String paddedSecond = padWithLeadingZeros(secondBinary, maxLength);

        StringBuilder result = new StringBuilder(maxLength + 1);
        char carry = ZERO;

        for (int index = maxLength - 1; index >= 0; index--) {
            char bitFirst = paddedFirst.charAt(index);
            char bitSecond = paddedSecond.charAt(index);

            char sumBit = calculateSumBit(bitFirst, bitSecond, carry);
            carry = calculateCarryBit(bitFirst, bitSecond, carry);

            result.append(sumBit);
        }

        if (carry == ONE) {
            result.append(ONE);
        }

        return result.reverse().toString();
    }
}