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
        int productOfAllModuli = 1;
        int solution = 0;

        for (int modulus : moduli) {
            productOfAllModuli *= modulus;
        }

        for (int i = 0; i < moduli.size(); i++) {
            int modulus = moduli.get(i);
            int remainder = remainders.get(i);

            int partialProduct = productOfAllModuli / modulus;
            int inverse = modularInverse(partialProduct, modulus);

            solution += remainder * partialProduct * inverse;
        }

        solution %= productOfAllModuli;
        if (solution < 0) {
            solution += productOfAllModuli;
        }

        return solution;
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

            int previousCoefficientTemp = previousCoefficient;
            previousCoefficient = currentCoefficient - quotient * previousCoefficient;
            currentCoefficient = previousCoefficientTemp;
        }

        if (currentCoefficient < 0) {
            currentCoefficient += originalModulus;
        }

        return currentCoefficient;
    }
}