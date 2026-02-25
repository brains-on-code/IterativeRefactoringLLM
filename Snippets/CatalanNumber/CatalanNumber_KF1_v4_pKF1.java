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

        for (int nodeCount = 2; nodeCount <= n; nodeCount++) {
            catalanNumbers[nodeCount] = 0;
            for (int leftSubtreeNodeCount = 0; leftSubtreeNodeCount < nodeCount; leftSubtreeNodeCount++) {
                int rightSubtreeNodeCount = nodeCount - leftSubtreeNodeCount - 1;
                catalanNumbers[nodeCount] +=
                    catalanNumbers[leftSubtreeNodeCount] * catalanNumbers[rightSubtreeNodeCount];
            }
        }

        return catalanNumbers[n];
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the number n to find nth Catalan number (n <= 50)");
            int n = scanner.nextInt();
            System.out.println(n + "th Catalan number is " + computeCatalanNumber(n));
        }
    }
}