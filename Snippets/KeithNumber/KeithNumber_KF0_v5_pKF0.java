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

        List<Integer> sequence = extractDigits(number);
        int digitCount = sequence.size();
        int currentIndex = digitCount;

        int nextTerm = sumLastTerms(sequence, currentIndex, digitCount);
        while (nextTerm < number) {
            sequence.add(nextTerm);
            currentIndex++;
            nextTerm = sumLastTerms(sequence, currentIndex, digitCount);
        }

        return nextTerm == number;
    }

    private static int sumLastTerms(List<Integer> sequence, int currentIndex, int termCount) {
        int sum = 0;
        for (int offset = 1; offset <= termCount; offset++) {
            sum += sequence.get(currentIndex - offset);
        }
        return sum;
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

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int number = scanner.nextInt();

            String message = isKeith(number)
                ? "Yes, the given number is a Keith number."
                : "No, the given number is not a Keith number.";

            System.out.println(message);
        }
    }
}