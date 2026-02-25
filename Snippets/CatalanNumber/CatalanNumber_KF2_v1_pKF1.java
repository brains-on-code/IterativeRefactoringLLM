package com.thealgorithms.dynamicprogramming;

import java.util.Scanner;

public final class CatalanNumber {

    private CatalanNumber() {
        // Prevent instantiation
    }

    static long computeCatalanNumber(int n) {
        long[] catalanNumbers = new long[n + 1];

        catalanNumbers[0] = 1;
        catalanNumbers[1] = 1;

        for (int currentIndex = 2; currentIndex <= n; currentIndex++) {
            catalanNumbers[currentIndex] = 0;
            for (int partitionIndex = 0; partitionIndex < currentIndex; partitionIndex++) {
                catalanNumbers[currentIndex] +=
                    catalanNumbers[partitionIndex] * catalanNumbers[currentIndex - partitionIndex - 1];
            }
        }

        return catalanNumbers[n];
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter n to find the nth Catalan number (n <= 50):");
            int n = scanner.nextInt();
            System.out.println(n + "th Catalan number is " + computeCatalanNumber(n));
        }
    }
}