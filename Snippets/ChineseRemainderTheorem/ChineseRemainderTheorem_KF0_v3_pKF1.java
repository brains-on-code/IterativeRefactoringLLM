package com.thealgorithms.maths;

import java.util.List;

/**
 * @brief Implementation of the Chinese Remainder Theorem (CRT) algorithm
 * @details
 * The Chinese Remainder Theorem (CRT) is used to solve systems of
 * simultaneous congruences. Given several pairwise coprime moduli
 * and corresponding remainders, the algorithm finds the smallest
 * positive solution.
 */
public final class ChineseRemainderTheorem {

    private ChineseRemainderTheorem() {
    }

    /**
     * @brief Solves the Chinese Remainder Theorem problem.
     * @param remainders The list of remainders.
     * @param moduli The list of pairwise coprime moduli.
     * @return The smallest positive solution that satisfies all the given congruences.
     */
    public static int solveCRT(List<Integer> remainders, List<Integer> moduli) {
        int combinedModulus = 1;
        int result = 0;

        for (int modulus : moduli) {
            combinedModulus *= modulus;
        }

        for (int index = 0; index < moduli.size(); index++) {
            int modulus = moduli.get(index);
            int remainder = remainders.get(index);

            int partialModulus = combinedModulus / modulus;
            int inverse = modularInverse(partialModulus, modulus);

            result += remainder * partialModulus * inverse;
        }

        result %= combinedModulus;
        if (result < 0) {
            result += combinedModulus;
        }

        return result;
    }

    /**
     * @brief Computes the modular inverse of a number with respect to a modulus using
     * the Extended Euclidean Algorithm.
     * @param value The number for which to find the inverse.
     * @param modulus The modulus.
     * @return The modular inverse of value modulo modulus.
     */
    private static int modularInverse(int value, int modulus) {
        int originalModulus = modulus;
        int previousCoefficient = 0;
        int currentCoefficient = 1;

        if (modulus == 1) {
            return 0;
        }

        int dividend = value;
        int divisor = modulus;

        while (dividend > 1) {
            int quotient = dividend / divisor;

            int previousDivisor = divisor;
            divisor = dividend % divisor;
            dividend = previousDivisor;

            int tempPreviousCoefficient = previousCoefficient;
            previousCoefficient = currentCoefficient - quotient * previousCoefficient;
            currentCoefficient = tempPreviousCoefficient;
        }

        if (currentCoefficient < 0) {
            currentCoefficient += originalModulus;
        }

        return currentCoefficient;
    }
}