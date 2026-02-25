package com.thealgorithms.maths.Prime;

import java.util.Scanner;

public final class PrimeChecker {

    private PrimeChecker() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        if (isPrimeDeterministic(number)) {
            System.out.println("Deterministic check: " + number + " is a prime number");
        } else {
            System.out.println("Deterministic check: " + number + " is not a prime number");
        }

        int fermatTestCount = 20;
        if (isPrimeProbabilistic(number, fermatTestCount)) {
            System.out.println("Probabilistic check: " + number + " is probably a prime number");
        } else {
            System.out.println("Probabilistic check: " + number + " is not a prime number");
        }

        scanner.close();
    }

    /**
     * Deterministic primality test using trial division up to sqrt(n).
     */
    public static boolean isPrimeDeterministic(int number) {
        if (number == 2) {
            return true;
        }
        if (number < 2 || number % 2 == 0) {
            return false;
        }
        int limit = (int) Math.sqrt(number);
        for (int divisor = 3; divisor <= limit; divisor += 2) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Probabilistic primality test using Fermat's little theorem.
     *
     * @param number       the number to test for primality
     * @param testCount    number of random bases to test
     * @return true if number is probably prime, false if composite
     */
    public static boolean isPrimeProbabilistic(int number, int testCount) {
        int minBase = 2;
        int maxBase = number - 2;

        for (int i = 0; i < testCount; i++) {
            long base =
                    (long) Math.floor(Math.random() * (maxBase - minBase + 1) + minBase);
            if (modularExponentiation(base, number - 1, number) != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes (base^exponent) % modulus using repeated multiplication.
     */
    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        for (long i = 0; i < exponent; i++) {
            result *= base;
            result %= modulus;
        }
        return result % modulus;
    }
}