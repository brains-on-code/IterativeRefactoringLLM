package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for checking Keith numbers.
 *
 * <p>A Keith number (or repfigit number) is an n-digit number N such that N
 * appears in a sequence where the first n terms are its digits and each
 * subsequent term is the sum of the previous n terms.</p>
 */
final class KeithNumber {

    private KeithNumber() {
        // Prevent instantiation
    }

    /**
     * Checks whether the given integer is a Keith number.
     *
     * @param number the number to check
     * @return {@code true} if {@code number} is a Keith number, {@code false} otherwise
     */
    static boolean isKeith(int number) {
        if (number <= 0) {
            return false;
        }

        List<Integer> terms = new ArrayList<>();
        int temp = number;
        int digitCount = 0;

        // Extract digits (in reverse order) and count them
        while (temp > 0) {
            terms.add(temp % 10);
            temp /= 10;
            digitCount++;
        }

        // Reverse to restore original digit order
        Collections.reverse(terms);

        int nextTerm = 0;
        int index = digitCount;

        // Generate sequence until it reaches or exceeds the original number
        while (nextTerm < number) {
            nextTerm = 0;

            // Sum the previous 'digitCount' terms
            for (int offset = 1; offset <= digitCount; offset++) {
                nextTerm += terms.get(index - offset);
            }

            terms.add(nextTerm);
            index++;
        }

        return nextTerm == number;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int input = scanner.nextInt();

            if (isKeith(input)) {
                System.out.println("Yes, the given number is a Keith number.");
            } else {
                System.out.println("No, the given number is not a Keith number.");
            }
        }
    }
}