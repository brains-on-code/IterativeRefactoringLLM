package com.thealgorithms.maths;

import java.util.Random;

/**
 * This class implements the Solovay-Strassen primality test,
 * which is a probabilistic algorithm to determine whether a number is prime.
 * The algorithm is based on properties of the Jacobi symbol and modular exponentiation.
 *
 * For more information, go to {@link https://en.wikipedia.org/wiki/Solovay%E2%80%93Strassen_primality_test}
 */
final class SolovayStrassenPrimalityTest {

    private final Random randomGenerator;

    /**
     * Constructs a SolovayStrassenPrimalityTest instance with a specified seed for randomness.
     *
     * @param seed the seed for generating random numbers
     */
    private SolovayStrassenPrimalityTest(int seed) {
        this.randomGenerator = new Random(seed);
    }

    /**
     * Factory method to create an instance of SolovayStrassenPrimalityTest.
     *
     * @param seed the seed for generating random numbers
     * @return a new instance of SolovayStrassenPrimalityTest
     */
    public static SolovayStrassenPrimalityTest createWithSeed(int seed) {
        return new SolovayStrassenPrimalityTest(seed);
    }

    /**
     * Calculates modular exponentiation using the method of exponentiation by squaring.
     *
     * @param base the base number
     * @param exponent the exponent
     * @param modulus the modulus
     * @return (base^exponent) mod modulus
     */
    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        long currentBase = base % modulus;

        while (exponent > 0) {
            boolean isExponentOdd = (exponent & 1) == 1;
            if (isExponentOdd) {
                result = (result * currentBase) % modulus;
            }
            currentBase = (currentBase * currentBase) % modulus;
            exponent /= 2;
        }

        return result % modulus;
    }

    /**
     * Computes the Jacobi symbol (a/n), which is a generalization of the Legendre symbol.
     *
     * @param numerator the numerator (a)
     * @param denominator the denominator (n, must be an odd positive integer)
     * @return the Jacobi symbol value: 1, -1, or 0
     */
    public int jacobiSymbol(long numerator, long denominator) {
        if (denominator <= 0 || denominator % 2 == 0) {
            return 0;
        }

        numerator %= denominator;
        int jacobiValue = 1;

        while (numerator != 0) {
            while (numerator % 2 == 0) {
                numerator /= 2;
                long denominatorMod8 = denominator % 8;
                if (denominatorMod8 == 3 || denominatorMod8 == 5) {
                    jacobiValue = -jacobiValue;
                }
            }

            long previousNumerator = numerator;
            numerator = denominator;
            denominator = previousNumerator;

            boolean bothThreeModFour = numerator % 4 == 3 && denominator % 4 == 3;
            if (bothThreeModFour) {
                jacobiValue = -jacobiValue;
            }

            numerator %= denominator;
        }

        return (denominator == 1) ? jacobiValue : 0;
    }

    /**
     * Performs the Solovay-Strassen primality test on a given number.
     *
     * @param candidate the number to be tested for primality
     * @param iterations the number of iterations to run for accuracy
     * @return true if candidate is likely prime, false if it is composite
     */
    public boolean isProbablePrime(long candidate, int iterations) {
        if (candidate <= 1) {
            return false;
        }
        if (candidate <= 3) {
            return true;
        }

        for (int iterationIndex = 0; iterationIndex < iterations; iterationIndex++) {
            long randomOffset = Math.abs(randomGenerator.nextLong() % (candidate - 1)) + 2;
            long base = randomOffset % (candidate - 1) + 1;

            long jacobiAdjusted = (candidate + jacobiSymbol(base, candidate)) % candidate;
            long exponent = (candidate - 1) / 2;
            long modularPower = modularExponentiation(base, exponent, candidate);

            if (jacobiAdjusted == 0 || modularPower != jacobiAdjusted) {
                return false;
            }
        }

        return true;
    }
}