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

        List<Integer> sequence = new ArrayList<>();
        int temp = number;

        // Extract digits
        while (temp > 0) {
            sequence.add(temp % 10);
            temp /= 10;
        }

        // Reverse to get the correct order of digits
        Collections.reverse(sequence);

        int numDigits = sequence.size();
        int current = 0;

        // Generate sequence until current >= number
        while (current < number) {
            current = 0;

            // Sum last numDigits terms
            for (int i = 1; i <= numDigits; i++) {
                current += sequence.get(sequence.size() - i);
            }

            sequence.add(current);
        }

        return current == number;
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