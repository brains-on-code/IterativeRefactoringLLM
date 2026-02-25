package com.thealgorithms.maths.prime;

import java.util.Scanner;

public final class PrimeCheck {

    private PrimeCheck() {
    }

    public static void main(String[] args) {
        try (Scanner inputScanner = new Scanner(System.in)) {
            System.out.print("Enter a number: ");
            int inputNumber = inputScanner.nextInt();

            if (isPrime(inputNumber)) {
                System.out.println("algo1 verify that " + inputNumber + " is a prime number");
            } else {
                System.out.println("algo1 verify that " + inputNumber + " is not a prime number");
            }

            int fermatTestIterations = 20;
            if (isProbablyPrimeFermat(inputNumber, fermatTestIterations)) {
                System.out.println("algo2 verify that " + inputNumber + " is a prime number");
            } else {
                System.out.println("algo2 verify that " + inputNumber + " is not a prime number");
            }
        }
    }

    /**
     * Checks if a number is prime or not using deterministic trial division.
     *
     * @param candidateNumber the number to test
     * @return {@code true} if {@code candidateNumber} is prime
     */
    public static boolean isPrime(int candidateNumber) {
        if (candidateNumber == 2) {
            return true;
        }
        if (candidateNumber < 2 || candidateNumber % 2 == 0) {
            return false;
        }
        int maxDivisor = (int) Math.sqrt(candidateNumber);
        for (int divisor = 3; divisor <= maxDivisor; divisor += 2) {
            if (candidateNumber % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Probabilistic primality test using Fermat's little theorem.
     *
     * @param candidateNumber the number to test
     * @param iterations      number of random bases to try
     * @return {@code true} if {@code candidateNumber} is probably prime
     */
    public static boolean isProbablyPrimeFermat(int candidateNumber, int iterations) {
        int minBase = 2;
        int maxBase = candidateNumber - 2;

        for (int iterationIndex = 0; iterationIndex < iterations; iterationIndex++) {
            long randomBase =
                    (long) Math.floor(Math.random() * (maxBase - minBase + 1) + minBase);
            if (modularExponentiation(randomBase, candidateNumber - 1, candidateNumber) != 1) {
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
        for (long exponentCounter = 0; exponentCounter < exponent; exponentCounter++) {
            result *= base;
            result %= modulus;
        }
        return result % modulus;
    }
}