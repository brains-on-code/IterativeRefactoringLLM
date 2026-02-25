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
        long[] catalan = new long[n + 1];

        catalan[0] = 1;
        catalan[1] = 1;

        for (int totalNodes = 2; totalNodes <= n; totalNodes++) {
            catalan[totalNodes] = 0;
            for (int leftTreeNodes = 0; leftTreeNodes < totalNodes; leftTreeNodes++) {
                int rightTreeNodes = totalNodes - leftTreeNodes - 1;
                catalan[totalNodes] += catalan[leftTreeNodes] * catalan[rightTreeNodes];
            }
        }

        return catalan[n];
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the number n to find nth Catalan number (n <= 50)");
            int n = scanner.nextInt();
            System.out.println(n + "th Catalan number is " + computeCatalanNumber(n));
        }
    }
}