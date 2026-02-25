package com.thealgorithms.dynamicprogramming;

import java.util.Scanner;

/**
 * Computes Catalan numbers using dynamic programming.
 */
public final class CatalanNumber {

    private CatalanNumber() {
    }

    /**
     * Computes the nth Catalan number using a bottom-up DP approach.
     *
     * @param n index of the Catalan number to compute (0-based)
     * @return the nth Catalan number
     */
    static long computeCatalanNumber(int n) {
        long[] catalanNumbers = new long[n + 1];

        catalanNumbers[0] = 1;
        catalanNumbers[1] = 1;

        for (int currentIndex = 2; currentIndex <= n; currentIndex++) {
            catalanNumbers[currentIndex] = 0;
            for (int leftSubtreeSize = 0; leftSubtreeSize < currentIndex; leftSubtreeSize++) {
                int rightSubtreeSize = currentIndex - leftSubtreeSize - 1;
                catalanNumbers[currentIndex] +=
                    catalanNumbers[leftSubtreeSize] * catalanNumbers[rightSubtreeSize];
            }
        }

        return catalanNumbers[n];
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the number n to find nth Catalan number (n <= 50)");
            int index = scanner.nextInt();
            System.out.println(index + "th Catalan number is " + computeCatalanNumber(index));
        }
    }
}