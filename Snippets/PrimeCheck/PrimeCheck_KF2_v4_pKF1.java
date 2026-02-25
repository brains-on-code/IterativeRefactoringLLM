package com.thealgorithms.maths.prime;

import java.util.Scanner;

public final class PrimeCheck {

    private static final int DEFAULT_FERMAT_ITERATIONS = 20;

    private PrimeCheck() {
    }

    public static void main(String[] args) {
        try (Scanner inputScanner = new Scanner(System.in)) {
            System.out.print("Enter a number: ");
            int candidateNumber = inputScanner.nextInt();

            if (isPrime(candidateNumber)) {
                System.out.println("algo1 verify that " + candidateNumber + " is a prime number");
            } else {
                System.out.println("algo1 verify that " + candidateNumber + " is not a prime number");
            }

            if (isProbablyPrimeFermat(candidateNumber, DEFAULT_FERMAT_ITERATIONS)) {
                System.out.println("algo2 verify that " + candidateNumber + " is a prime number");
            } else {
                System.out.println("algo2 verify that " + candidateNumber + " is not a prime number");
            }
        }
    }

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

    public static boolean isProbablyPrimeFermat(int candidateNumber, int iterationCount) {
        if (candidateNumber <= 3) {
            return candidateNumber > 1;
        }

        int minBase = 2;
        int maxBase = candidateNumber - 2;

        for (int iterationIndex = 0; iterationIndex < iterationCount; iterationIndex++) {
            long randomBase = (long) Math.floor(
                Math.random() * (maxBase - minBase + 1) + minBase
            );
            if (modularExponentiation(randomBase, candidateNumber - 1, candidateNumber) != 1) {
                return false;
            }
        }
        return true;
    }

    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        for (long exponentIndex = 0; exponentIndex < exponent; exponentIndex++) {
            result *= base;
            result %= modulus;
        }
        return result % modulus;
    }
}