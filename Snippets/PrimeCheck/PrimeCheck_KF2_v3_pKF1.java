package com.thealgorithms.maths.prime;

import java.util.Scanner;

public final class PrimeCheck {

    private static final int DEFAULT_FERMAT_ITERATIONS = 20;

    private PrimeCheck() {
    }

    public static void main(String[] args) {
        try (Scanner inputScanner = new Scanner(System.in)) {
            System.out.print("Enter a number: ");
            int number = inputScanner.nextInt();

            if (isPrime(number)) {
                System.out.println("algo1 verify that " + number + " is a prime number");
            } else {
                System.out.println("algo1 verify that " + number + " is not a prime number");
            }

            if (isProbablyPrimeFermat(number, DEFAULT_FERMAT_ITERATIONS)) {
                System.out.println("algo2 verify that " + number + " is a prime number");
            } else {
                System.out.println("algo2 verify that " + number + " is not a prime number");
            }
        }
    }

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

    public static boolean isProbablyPrimeFermat(int number, int iterations) {
        if (number <= 3) {
            return number > 1;
        }

        int minBase = 2;
        int maxBase = number - 2;

        for (int i = 0; i < iterations; i++) {
            long randomBase = (long) Math.floor(
                Math.random() * (maxBase - minBase + 1) + minBase
            );
            if (modularExponentiation(randomBase, number - 1, number) != 1) {
                return false;
            }
        }
        return true;
    }

    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        for (long i = 0; i < exponent; i++) {
            result *= base;
            result %= modulus;
        }
        return result % modulus;
    }
}