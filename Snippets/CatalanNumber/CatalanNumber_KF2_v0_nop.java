package com.thealgorithms.dynamicprogramming;

import java.util.Scanner;

public final class CatalanNumber {
    private CatalanNumber() {
    }


    static long findNthCatalan(int n) {
        long[] catalanArray = new long[n + 1];

        catalanArray[0] = 1;
        catalanArray[1] = 1;


        for (int i = 2; i <= n; i++) {
            catalanArray[i] = 0;
            for (int j = 0; j < i; j++) {
                catalanArray[i] += catalanArray[j] * catalanArray[i - j - 1];
            }
        }

        return catalanArray[n];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number n to find nth Catalan number (n <= 50)");
        int n = sc.nextInt();
        System.out.println(n + "th Catalan number is " + findNthCatalan(n));

        sc.close();
    }
}
