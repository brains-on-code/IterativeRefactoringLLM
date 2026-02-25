package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

final class KeithNumber {

    private KeithNumber() {
        // Utility class; prevent instantiation
    }

    static boolean isKeith(int number) {
        if (number <= 0) {
            return false;
        }

        List<Integer> terms = extractDigits(number);
        int digitCount = terms.size();

        int nextTerm = 0;
        int index = digitCount;

        while (nextTerm < number) {
            nextTerm = 0;

            for (int j = 1; j <= digitCount; j++) {
                nextTerm += terms.get(index - j);
            }

            terms.add(nextTerm);
            index++;
        }

        return nextTerm == number;
    }

    private static List<Integer> extractDigits(int number) {
        List<Integer> digits = new ArrayList<>();

        int temp = number;
        while (temp > 0) {
            digits.add(temp % 10);
            temp /= 10;
        }

        Collections.reverse(digits);
        return digits;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();

            if (isKeith(n)) {
                System.out.println("Yes, the given number is a Keith number.");
            } else {
                System.out.println("No, the given number is not a Keith number.");
            }
        }
    }
}