package com.thealgorithms.maths;


public final class PerfectNumber {
    private PerfectNumber() {
    }


    public static boolean isPerfectNumber(int number) {
        if (number <= 0) {
            return false;
        }
        int sum = 0;

        for (int i = 1; i < number; ++i) {
            if (number % i == 0) {
                sum += i;
            }
        }
        return sum == number;
    }


    public static boolean isPerfectNumber2(int n) {
        if (n <= 0) {
            return false;
        }
        int sum = 1;
        double root = Math.sqrt(n);


        for (int i = 2; i <= root; i++) {
            if (n % i == 0) {
                sum += i + n / i;
            }
        }

        if (root == (int) root) {
            sum -= root;
        }

        return sum == n;
    }
}
