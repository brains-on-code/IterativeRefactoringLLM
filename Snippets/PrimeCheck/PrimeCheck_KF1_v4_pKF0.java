package com.thealgorithms.maths.Prime;

import java.util.Scanner;

public final class PrimeChecker {

    private static final int DEFAULT_FERMAT_ITERATIONS = 20;

    private PrimeChecker() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int number = readNumber(scanner);
            printDeterministicResult(number);
            printFermatResult(number, DEFAULT_FERMAT_ITERATIONS);
        }
    }

    private static int readNumber(Scanner scanner) {
        System.out.print("Enter a number: ");
        return scanner.nextInt();
    }

    private static void printDeterministicResult(int number) {
        boolean isPrime = isPrimeDeterministic(number);
        printPrimeCheckResult("Deterministic", number, isPrime);
    }

    private static void printFermatResult(int number, int iterations) {
        boolean isPrime = isPrimeFermat(number, iterations);
        printPrimeCheckResult("Fermat", number, isPrime);
    }

    private static void printPrimeCheckResult(String algorithmName, int number, boolean isPrime) {
        String result = isPrime ? "is a prime number" : "is not a prime number";
        System.out.printf("%s check: %d %s%n", algorithmName, number, result);
    }

    /**
     * Deterministic primality test using trial division up to sqrt(n).
     */
    public static boolean isPrimeDeterministic(int n) {
        if (n == 2) {
            return true;
        }
        if (n < 2 || n % 2 == 0) {
            return false;
        }

        int limit = (int) Math.sqrt(n);
        for (int divisor = 3; divisor <= limit; divisor += 2) {
            if (n % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Probabilistic primality test using Fermat's little theorem.
     *
     * @param n          number to test
     * @param iterations number of random bases to try
     * @return true if n is probably prime, false if composite
     */
    public static boolean isPrimeFermat(int n, int iterations) {
        if (n <= 3) {
            return n > 1;
        }

        int minBase = 2;
        int maxBase = n - 2;

        for (int i = 0; i < iterations; i++) {
            long base = randomLongInRange(minBase, maxBase);
            long exponent = n - 1L;
            if (modularExponentiation(base, exponent, n) != 1L) {
                return false;
            }
        }
        return true;
    }

    private static long randomLongInRange(int min, int max) {
        long range = (long) max - min + 1L;
        return (long) (Math.random() * range) + min;
    }

    /**
     * Computes (base^exponent) mod modulus using fast modular exponentiation.
     */
    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1L;
        base %= modulus;

        long currentExponent = exponent;
        while (currentExponent > 0) {
            if ((currentExponent & 1L) == 1L) {
                result = (result * base) % modulus;
            }
            base = (base * base) % modulus;
            currentExponent >>= 1;
        }

        return result;
    }
}