package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for working with Keith numbers.
 *
 * A Keith number (or repfigit number) is an n-digit number N with the following property:
 * - Take its n digits as the first n terms of a sequence.
 * - Each subsequent term is the sum of the previous n terms.
 * - If N appears in this sequence, then N is a Keith number.
 *
 * Example: 197
 *   Digits: 1, 9, 7  (n = 3)
 *   Sequence: 1, 9, 7, 17, 33, 57, 107, 197, ...
 *   Since 197 appears in the sequence, 197 is a Keith number.
 */
final class KeithNumberUtils {

    private KeithNumberUtils() {
        // Prevent instantiation
    }

    /**
     * Checks whether the given integer is a Keith number.
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

        // Generate sequence terms until we reach or exceed the original number
        while (currentTerm < number) {
            currentTerm = sumLastTerms(sequence, numDigits);
            sequence.add(currentTerm);
        }

        return currentTerm == number;
    }

    /**
     * Extracts the digits of a number in left-to-right order.
     *
     * @param number the number whose digits are to be extracted
     * @return a list of digits in left-to-right order
     */
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

    /**
     * Sums the last {@code count} terms of the given sequence.
     *
     * @param sequence the sequence of integers
     * @param count    the number of last terms to sum
     * @return the sum of the last {@code count} terms
     */
    private static int sumLastTerms(List<Integer> sequence, int count) {
        int sum = 0;
        int startIndex = sequence.size() - count;

        for (int i = startIndex; i < sequence.size(); i++) {
            sum += sequence.get(i);
        }

        return sum;
    }

    /**
     * Reads an integer from standard input and prints whether it is a Keith number.
     *
     * @param args command-line arguments (not used)
     */
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