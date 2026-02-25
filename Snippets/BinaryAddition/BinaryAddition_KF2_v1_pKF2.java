package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

public class BinaryAddition {

    private static final char ONE = '1';
    private static final char ZERO = '0';

    private int countOnes(char a, char b, char carry) {
        int count = 0;
        if (a == ONE) count++;
        if (b == ONE) count++;
        if (carry == ONE) count++;
        return count;
    }

    public char sum(char a, char b, char carry) {
        int onesCount = countOnes(a, b, carry);
        return (onesCount % 2 == 0) ? ZERO : ONE;
    }

    public char carry(char a, char b, char carry) {
        int onesCount = countOnes(a, b, carry);
        return (onesCount >= 2) ? ONE : ZERO;
    }

    private String padWithLeadingZeros(String value, int targetLength) {
        int zerosToAdd = targetLength - value.length();
        if (zerosToAdd <= 0) {
            return value;
        }
        String padding = String.join("", Collections.nCopies(zerosToAdd, "0"));
        return padding + value;
    }

    public String addBinary(String a, String b) {
        int maxLength = Math.max(a.length(), b.length());
        a = padWithLeadingZeros(a, maxLength);
        b = padWithLeadingZeros(b, maxLength);

        StringBuilder result = new StringBuilder();
        char carry = ZERO;

        for (int i = maxLength - 1; i >= 0; i--) {
            char bitA = a.charAt(i);
            char bitB = b.charAt(i);

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