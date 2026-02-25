package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

final class KeithNumber {

    private KeithNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether the given number is a Keith number.
     *
     * A Keith number is an n-digit number N with the following property:
     * - Take its n digits as the first n terms of a sequence.
     * - Each subsequent term is the sum of the previous n terms.
     * - If N appears in this sequence, then N is a Keith number.
     *
     * @param number the number to check
     * @return true if the number is a Keith number, false otherwise
     */
    static boolean isKeithNumber(int number) {
        if (number <= 0) {
            return false;
        }

        List<Integer> sequence = extractDigits(number);
        int numDigits = sequence.size();
        int currentTerm = 0;

        while (currentTerm < number) {
            currentTerm = sumLastTerms(sequence, numDigits);
            sequence.add(currentTerm);
        }

        return currentTerm == number;
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

    private static int sumLastTerms(List<Integer> sequence, int count) {
        int sum = 0;
        int size = sequence.size();

        for (int i = 1; i <= count; i++) {
            sum += sequence.get(size - i);
        }

        return sum;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int input = scanner.nextInt();
            if (isKeithNumber(input)) {
                System.out.println("Yes, the given number is a Keith number.");
            } else {
                System.out.println("No, the given number is not a Keith number.");
            }
        }
    }
}