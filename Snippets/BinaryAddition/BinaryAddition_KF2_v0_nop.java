package com.thealgorithms.greedyalgorithms;

import java.util.Collections;


public class BinaryAddition {

    public char sum(char a, char b, char carry) {
        int count = 0;
        if (a == '1') {
            count++;
        }
        if (b == '1') {
            count++;
        }
        if (carry == '1') {
            count++;
        }
        return count % 2 == 0 ? '0' : '1';
    }

    public char carry(char a, char b, char carry) {
        int count = 0;
        if (a == '1') {
            count++;
        }
        if (b == '1') {
            count++;
        }
        if (carry == '1') {
            count++;
        }
        return count >= 2 ? '1' : '0';
    }

    public String addBinary(String a, String b) {
        int maxLength = Math.max(a.length(), b.length());
        a = String.join("", Collections.nCopies(maxLength - a.length(), "0")) + a;
        b = String.join("", Collections.nCopies(maxLength - b.length(), "0")) + b;
        StringBuilder result = new StringBuilder();
        char carry = '0';
        for (int i = maxLength - 1; i >= 0; i--) {
            char sum = sum(a.charAt(i), b.charAt(i), carry);
            carry = carry(a.charAt(i), b.charAt(i), carry);
            result.append(sum);
        }
        if (carry == '1') {
            result.append('1');
        }
        return result.reverse().toString();
    }
}
