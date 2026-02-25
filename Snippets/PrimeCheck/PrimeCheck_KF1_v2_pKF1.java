package com.thealgorithms.maths.Prime;

import java.util.Scanner;

public final class PrimeChecker {

    private PrimeChecker() {
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int candidateNumber = inputScanner.nextInt();

        if (isPrimeDeterministic(candidateNumber)) {
            System.out.println("Deterministic check: " + candidateNumber + " is a prime number");
        } else {
            System.out.println("Deterministic check: " + candidateNumber + " is not a prime number");
        }

        int fermatIterations = 20;
        if (isPrimeProbabilistic(candidateNumber, fermatIterations)) {
            System.out.println("Probabilistic check: " + candidateNumber + " is probably a prime number");
        } else {
            System.out.println("Probabilistic check: " + candidateNumber + " is not a prime number");
        }

        inputScanner.close();
    }

    /**
     * Deterministic primality test using trial division up to sqrt(n).
     */
    public static boolean isPrimeDeterministic(int candidateNumber) {
        if (candidateNumber == 2) {
            return true;
        }
        if (candidateNumber < 2 || candidateNumber % 2 == 0) {
            return false;
        }
        int squareRoot = (int) Math.sqrt(candidateNumber);
        for (int divisor = 3; divisor <= squareRoot; divisor += 2) {
            if (candidateNumber % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Probabilistic primality test using Fermat's little theorem.
     *
     * @param candidateNumber the number to test for primality
     * @param iterationCount  number of random bases to test
     * @return true if number is probably prime, false if composite
     */
    public static boolean isPrimeProbabilistic(int candidateNumber, int iterationCount) {
        int lowerBaseBound = 2;
        int upperBaseBound = candidateNumber - 2;

        for (int iterationIndex = 0; iterationIndex < iterationCount; iterationIndex++) {
            long randomBase =
                    (long) Math.floor(Math.random() * (upperBaseBound - lowerBaseBound + 1) + lowerBaseBound);
            if (modularExponentiation(randomBase, candidateNumber - 1, candidateNumber) != 1) {
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
        for (long exponentCounter = 0; exponentCounter < exponent; exponentCounter++) {
            result *= base;
            result %= modulus;
        }
        return result % modulus;
    }
}