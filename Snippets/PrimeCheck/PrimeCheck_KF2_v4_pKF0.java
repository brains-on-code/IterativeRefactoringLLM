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
            int number = promptForNumber(scanner);
            printDeterministicResult(number);
            printFermatResult(number);
        }
    }

    private static int promptForNumber(Scanner scanner) {
        System.out.print("Enter a number: ");
        return scanner.nextInt();
    }

    private static void printDeterministicResult(int number) {
        String resultMessage = isPrime(number) ? "is a prime number" : "is not a prime number";
        System.out.println("Deterministic check: " + number + " " + resultMessage);
    }

    private static void printFermatResult(int number) {
        String resultMessage =
                isProbablyPrimeFermat(number, DEFAULT_FERMAT_ITERATIONS)
                        ? "is a prime number"
                        : "is not a prime number";
        System.out.println("Fermat check: " + number + " " + resultMessage);
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
            long base = getRandomLongInRange(lowerBound, upperBound);
            if (modularExponentiation(base, number - 1, number) != 1) {
                return false;
            }
        }
        return true;
    }

    private static long getRandomLongInRange(int min, int max) {
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