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

    public char sum(char a, char b, char carry) {
        return countOnes(a, b, carry) % 2 == 0 ? ZERO : ONE;
    }

    public char carry(char a, char b, char carry) {
        return countOnes(a, b, carry) >= 2 ? ONE : ZERO;
    }

    private String padWithLeadingZeros(String value, int targetLength) {
        int paddingLength = targetLength - value.length();
        if (paddingLength <= 0) {
            return value;
        }
        String padding = String.join("", Collections.nCopies(paddingLength, "0"));
        return padding + value;
    }

    public String addBinary(String a, String b) {
        int maxLength = Math.max(a.length(), b.length());
        String paddedA = padWithLeadingZeros(a, maxLength);
        String paddedB = padWithLeadingZeros(b, maxLength);

        StringBuilder result = new StringBuilder();
        char carry = ZERO;

        for (int i = maxLength - 1; i >= 0; i--) {
            char bitA = paddedA.charAt(i);
            char bitB = paddedB.charAt(i);

            char sumBit = sum(bitA, bitB, carry);
            carry = carry(bitA, bitB, carry);

            result.append(sumBit);
        }

        if (carry == ONE) {
            result.append(ONE);
        }

        return result.reverse().toString();
    }
}