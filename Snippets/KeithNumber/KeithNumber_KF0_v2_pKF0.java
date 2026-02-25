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

    private static int sumLastTerms(List<Integer> sequence, int currentIndex, int count) {
        int sum = 0;
        for (int offset = 1; offset <= count; offset++) {
            sum += sequence.get(currentIndex - offset);
        }
        return sum;
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
            int number = scanner.nextInt();

            if (isKeith(number)) {
                System.out.println("Yes, the given number is a Keith number.");
            } else {
                System.out.println("No, the given number is not a Keith number.");
            }
        }
    }
}