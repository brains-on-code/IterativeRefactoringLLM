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
        int digitCount = sequence.size();
        int currentTerm = sequence.get(sequence.size() - 1);

        while (currentTerm < number) {
            currentTerm = sumLastTerms(sequence, digitCount);
            sequence.add(currentTerm);
        }

        return currentTerm == number;
    }

    private static List<Integer> extractDigits(int number) {
        List<Integer> digits = new ArrayList<>();
        int remaining = number;

        while (remaining > 0) {
            digits.add(remaining % 10);
            remaining /= 10;
        }

        Collections.reverse(digits);
        return digits;
    }

    private static int sumLastTerms(List<Integer> sequence, int termCount) {
        int sum = 0;
        int size = sequence.size();

        for (int i = size - termCount; i < size; i++) {
            sum += sequence.get(i);
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