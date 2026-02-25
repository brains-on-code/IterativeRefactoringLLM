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
final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Checks whether the given integer is a Keith number.
     *
     * @param number the number to check
     * @return true if the number is a Keith number, false otherwise
     */
    static boolean method1(int number) {
        if (number <= 0) {
            return false;
        }

        List<Integer> sequence = new ArrayList<>();
        int temp = number;

        // Extract digits of the number (in reverse order)
        while (temp > 0) {
            sequence.add(temp % 10);
            temp /= 10;
        }

        // Reverse to get digits in the correct order
        Collections.reverse(sequence);

        int numDigits = sequence.size();
        int currentTerm = 0;
        int sequenceSize = numDigits;

        // Generate sequence terms until currentTerm >= number
        while (currentTerm < number) {
            currentTerm = 0;

            // Sum the last numDigits terms
            for (int i = 1; i <= numDigits; i++) {
                currentTerm += sequence.get(sequenceSize - i);
            }

            sequence.add(currentTerm);
            sequenceSize++;
        }

        return currentTerm == number;
    }

    /**
     * Reads an integer from standard input and prints whether it is a Keith number.
     *
     * @param args command-line arguments (not used)
     */
    public static void method2(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        if (method1(input)) {
            System.out.println("Yes, the given number is a Keith number.");
        } else {
            System.out.println("No, the given number is not a Keith number.");
        }

        scanner.close();
    }
}