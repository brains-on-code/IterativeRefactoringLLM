package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

/**
 * Performs binary addition on binary strings.
 */
public class BinaryAddition {

    /**
     * Returns the sum bit of three binary bits (a, b, carryIn).
     */
    public char sum(char a, char b, char carryIn) {
        int onesCount = 0;
        if (a == '1') {
            onesCount++;
        }
        if (b == '1') {
            onesCount++;
        }
        if (carryIn == '1') {
            onesCount++;
        }
        return (onesCount % 2 == 0) ? '0' : '1';
    }

    /**
     * Returns the carry-out bit of three binary bits (a, b, carryIn).
     */
    public char carry(char a, char b, char carryIn) {
        int onesCount = 0;
        if (a == '1') {
            onesCount++;
        }
        if (b == '1') {
            onesCount++;
        }
        if (carryIn == '1') {
            onesCount++;
        }
        return (onesCount >= 2) ? '1' : '0';
    }

    /**
     * Adds two binary strings and returns their sum as a binary string.
     */
    public String addBinary(String a, String b) {
        int maxLength = Math.max(a.length(), b.length());

        a = String.join("", Collections.nCopies(maxLength - a.length(), "0")) + a;
        b = String.join("", Collections.nCopies(maxLength - b.length(), "0")) + b;

        StringBuilder result = new StringBuilder();
        char carry = '0';

        for (int i = maxLength - 1; i >= 0; i--) {
            char bitA = a.charAt(i);
            char bitB = b.charAt(i);

            char sumBit = sum(bitA, bitB, carry);
            carry = carry(bitA, bitB, carry);

            result.append(sumBit);
        }

        if (carry == '1') {
            result.append('1');
        }

        return result.reverse().toString();
    }
}