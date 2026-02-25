package com.thealgorithms.maths.Prime;

import java.util.Random;
import java.util.Scanner;

public final class PrimeCheck {

    private static final int DEFAULT_FERMAT_ITERATIONS = 20;
    private static final Random RANDOM = new Random();

    private PrimeCheck() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int number = readNumber(scanner);
            printDeterministicCheck(number);
            printFermatCheck(number);
        }
    }

    private static int readNumber(Scanner scanner) {
        System.out.print("Enter a number: ");
        return scanner.nextInt();
    }

    private static void printDeterministicCheck(int number) {
        System.out.printf(
                "Deterministic check: %d %s%n",
                number,
                isPrime(number) ? "is a prime number" : "is not a prime number"
        );
    }

    private static void printFermatCheck(int number) {
        System.out.printf(
                "Fermat check: %d %s%n",
                number,
                isProbablyPrimeFermat(number, DEFAULT_FERMAT_ITERATIONS)
                        ? "is a prime number"
                        : "is not a prime number"
        );
    }

    public static boolean isPrime(int number) {
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

    public static boolean isProbablyPrimeFermat(int number, int iterations) {
        if (number <= 3) {
            return number > 1;
        }

        int lowerBound = 2;
        int upperBound = number - 2;

        for (int i = 0; i < iterations; i++) {
            long base = randomLongInRange(lowerBound, upperBound);
            if (modularExponentiation(base, number - 1, number) != 1) {
                return false;
            }
        }
        return true;
    }

    private static long randomLongInRange(int min, int max) {
        return min + (long) RANDOM.nextInt(max - min + 1);
    }

    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        long currentBase = base % modulus;
        long currentExponent = exponent;

        while (currentExponent > 0) {
            if ((currentExponent & 1) == 1) {
                result = (result * currentBase) % modulus;
            }
            currentBase = (currentBase * currentBase) % modulus;
            currentExponent >>= 1;
        }

        return result;
    }
}