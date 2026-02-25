package com.thealgorithms.greedyalgorithms;

import java.util.Collections;

/**
 * Utility class for binary addition using character-based operations.
 */
public class Class1 {

    /**
     * Computes the sum bit of a full-adder for three binary inputs.
     *
     * @param a first binary digit ('0' or '1')
     * @param b second binary digit ('0' or '1')
     * @param carryIn carry-in binary digit ('0' or '1')
     * @return the sum bit ('0' or '1')
     */
    public char method1(char a, char b, char carryIn) {
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
        return onesCount % 2 == 0 ? '0' : '1';
    }

    /**
     * Computes the carry-out bit of a full-adder for three binary inputs.
     *
     * @param a first binary digit ('0' or '1')
     * @param b second binary digit ('0' or '1')
     * @param carryIn carry-in binary digit ('0' or '1')
     * @return the carry-out bit ('0' or '1')
     */
    public char method2(char a, char b, char carryIn) {
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
        return onesCount >= 2 ? '1' : '0';
    }

    /**
     * Adds two binary numbers represented as strings.
     *
     * @param firstBinary  first binary number as a string
     * @param secondBinary second binary number as a string
     * @return the binary sum of the two inputs as a string
     */
    public String method3(String firstBinary, String secondBinary) {
        int maxLength = Math.max(firstBinary.length(), secondBinary.length());

        // Left-pad the shorter string with zeros so both have equal length
        firstBinary =
                String.join("", Collections.nCopies(maxLength - firstBinary.length(), "0")) + firstBinary;
        secondBinary =
                String.join("", Collections.nCopies(maxLength - secondBinary.length(), "0")) + secondBinary;

        StringBuilder result = new StringBuilder();
        char carry = '0';

        // Process bits from least significant to most significant
        for (int i = maxLength - 1; i >= 0; i--) {
            char sumBit = method1(firstBinary.charAt(i), secondBinary.charAt(i), carry);
            carry = method2(firstBinary.charAt(i), secondBinary.charAt(i), carry);
            result.append(sumBit);
        }

        // Append remaining carry if present
        if (carry == '1') {
            result.append('1');
        }

        // Reverse to obtain the correct order (most significant bit first)
        return result.reverse().toString();
    }
}