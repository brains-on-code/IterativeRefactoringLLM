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
        String result = isPrime(number) ? "is a prime number" : "is not a prime number";
        System.out.println("algo1 verify that " + number + " " + result);
    }

    private static void printFermatCheck(int number) {
        String result =
                isProbablyPrimeFermat(number, DEFAULT_FERMAT_ITERATIONS)
                        ? "is a prime number"
                        : "is not a prime number";
        System.out.println("algo2 verify that " + number + " " + result);
    }

    public static boolean isPrime(int n) {
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

    public static boolean isProbablyPrimeFermat(int n, int iterations) {
        if (n <= 3) {
            return n > 1;
        }

        int lowerBound = 2;
        int upperBound = n - 2;

        for (int i = 0; i < iterations; i++) {
            long base = getRandomLongInRange(lowerBound, upperBound);
            if (modPow(base, n - 1, n) != 1) {
                return false;
            }
        }
        return true;
    }

    private static long getRandomLongInRange(int min, int max) {
        return min + (long) RANDOM.nextInt(max - min + 1);
    }

    private static long modPow(long base, long exponent, long modulus) {
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