package com.thealgorithms.maths.Prime;

import java.util.Scanner;

public final class PrimeCheck {

    private PrimeCheck() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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

        scanner.close();
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
            long base = (long) Math.floor(Math.random() * (upperBound - lowerBound + 1) + lowerBound);
            if (modularExponentiation(base, number - 1, number) != 1) {
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