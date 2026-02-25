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

        List<Integer> sequence = getDigits(number);
        int digitCount = sequence.size();
        int nextTerm = 0;
        int currentIndex = digitCount;

        while (nextTerm < number) {
            nextTerm = sumLastTerms(sequence, currentIndex, digitCount);
            sequence.add(nextTerm);
            currentIndex++;
        }

        return nextTerm == number;
    }

    private static List<Integer> getDigits(int number) {
        List<Integer> digits = new ArrayList<>();
        int remaining = number;

        while (remaining > 0) {
            digits.add(remaining % 10);
            remaining /= 10;
        }

        Collections.reverse(digits);
        return digits;
    }

    private static int sumLastTerms(List<Integer> terms, int currentIndex, int count) {
        int sum = 0;
        for (int offset = 1; offset <= count; offset++) {
            sum += terms.get(currentIndex - offset);
        }
        return sum;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int inputNumber = scanner.nextInt();

            if (isKeith(inputNumber)) {
                System.out.println("Yes, the given number is a Keith number.");
            } else {
                System.out.println("No, the given number is not a Keith number.");
            }
        }
    }
}