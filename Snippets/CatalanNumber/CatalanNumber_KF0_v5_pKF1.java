package com.thealgorithms.dynamicprogramming;

import java.util.Scanner;

/**
 * This file contains an implementation of finding the nth CATALAN NUMBER using
 * dynamic programming : <a href="https://en.wikipedia.org/wiki/Catalan_number">Wikipedia</a>
 *
 * Time Complexity: O(n^2) Space Complexity: O(n)
 *
 * @author <a href="https://github.com/amritesh19">AMRITESH ANAND</a>
 */
public final class CatalanNumber {

    private CatalanNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the nth Catalan number.
     *
     * @param n index of the Catalan number to compute; should be <= 50
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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number n to find nth Catalan number (n <= 50)");
        int n = scanner.nextInt();
        System.out.println(n + "th Catalan number is " + computeCatalanNumber(n));

        scanner.close();
    }
}