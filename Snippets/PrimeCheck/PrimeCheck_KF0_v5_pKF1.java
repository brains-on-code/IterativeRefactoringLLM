package com.thealgorithms.maths.prime;

import java.util.Scanner;

public final class PrimeCheck {

    private PrimeCheck() {
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter a number: ");
            int number = scanner.nextInt();

            if (isPrime(number)) {
                System.out.println("algo1 verify that " + number + " is a prime number");
            } else {
                System.out.println("algo1 verify that " + number + " is not a prime number");
            }

            int fermatIterations = 20;
            if (isProbablyPrimeFermat(number, fermatIterations)) {
                System.out.println("algo2 verify that " + number + " is a prime number");
            } else {
                System.out.println("algo2 verify that " + number + " is not a prime number");
            }
        }
    }

    /**
     * Checks if a number is prime or not using deterministic trial division.
     *
     * @param number the number to test
     * @return {@code true} if {@code number} is prime
     */
    public static boolean isPrime(int number) {
        if (number == 2) {
            return true;
        }
        if (number < 2 || number % 2 == 0) {
            return false;
        }
        int maxDivisor = (int) Math.sqrt(number);
        for (int divisor = 3; divisor <= maxDivisor; divisor += 2) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Probabilistic primality test using Fermat's little theorem.
     *
     * @param number     the number to test
     * @param iterations number of random bases to try
     * @return {@code true} if {@code number} is probably prime
     */
    public static boolean isProbablyPrimeFermat(int number, int iterations) {
        int minBase = 2;
        int maxBase = number - 2;

        for (int i = 0; i < iterations; i++) {
            long base =
                    (long) Math.floor(Math.random() * (maxBase - minBase + 1) + minBase);
            if (modularExponentiation(base, number - 1, number) != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes (base^exponent) mod modulus using repeated multiplication.
     *
     * @param base     the base
     * @param exponent the exponent
     * @param modulus  the modulus
     * @return (base^exponent) mod modulus
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