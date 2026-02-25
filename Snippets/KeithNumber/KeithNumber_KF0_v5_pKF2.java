package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

final class KeithNumber {

    private KeithNumber() {}

    /**
     * Checks whether the given number is a Keith number.
     *
     * A Keith number is an n-digit number N that appears in a sequence where:
     * <ul>
     *     <li>The first n terms are the digits of N.</li>
     *     <li>Each subsequent term is the sum of the previous n terms.</li>
     * </ul>
     *
     * @param number the number to test
     * @return {@code true} if {@code number} is a Keith number; {@code false} otherwise
     */
    static boolean isKeith(int number) {
        if (number <= 0) {
            return false;
        }

        List<Integer> sequence = new ArrayList<>();
        int temp = number;
        int digitCount = 0;

        // Extract digits (in reverse order) and count them
        while (temp > 0) {
            sequence.add(temp % 10);
            temp /= 10;
            digitCount++;
        }

        // Reverse to restore the original digit order
        Collections.reverse(sequence);

        int nextTerm = 0;
        int index = digitCount;

        // Generate terms until the sequence reaches or exceeds the original number
        while (nextTerm < number) {
            nextTerm = 0;

            // Next term is the sum of the previous 'digitCount' terms
            for (int offset = 1; offset <= digitCount; offset++) {
                nextTerm += sequence.get(index - offset);
            }

            sequence.add(nextTerm);
            index++;
        }

        return nextTerm == number;
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            int n = in.nextInt();

            if (isKeith(n)) {
                System.out.println("Yes, the given number is a Keith number.");
            } else {
                System.out.println("No, the given number is not a Keith number.");
            }
        }
    }
}