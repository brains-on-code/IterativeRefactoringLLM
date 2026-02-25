package com.thealgorithms.maths;

import java.util.List;

/**
 * Chinese Remainder Theorem implementation.
 */
public final class ChineseRemainderTheorem {
    private ChineseRemainderTheorem() {
    }

    /**
     * Solves a system of congruences using the Chinese Remainder Theorem.
     *
     * @param remainders list of remainders (a_i)
     * @param moduli     list of moduli (n_i), assumed pairwise coprime
     * @return the smallest non-negative solution x such that x ≡ a_i (mod n_i) for all i
     */
    public static int solve(List<Integer> remainders, List<Integer> moduli) {
        int combinedModulus = 1;
        int solution = 0;

        for (int modulus : moduli) {
            combinedModulus *= modulus;
        }

        for (int index = 0; index < moduli.size(); index++) {
            int currentModulus = moduli.get(index);
            int currentRemainder = remainders.get(index);

            int partialModulus = combinedModulus / currentModulus;
            int inverse = modularInverse(partialModulus, currentModulus);

            solution += currentRemainder * partialModulus * inverse;
        }

        solution %= combinedModulus;
        if (solution < 0) {
            solution += combinedModulus;
        }

        return solution;
    }

    /**
     * Computes the modular multiplicative inverse of a modulo m using the Extended Euclidean Algorithm.
     *
     * @param value   the number to invert
     * @param modulus the modulus
     * @return the modular inverse of value modulo modulus
     */
    private static int modularInverse(int value, int modulus) {
        int originalModulus = modulus;
        int previousCoefficient = 0;
        int currentCoefficient = 1;

        if (modulus == 1) {
            return 0;
        }

        int currentValue = value;
        int currentModulus = modulus;

        while (currentValue > 1) {
            int quotient = currentValue / currentModulus;
            int tempModulus = currentModulus;

            currentModulus = currentValue % currentModulus;
            currentValue = tempModulus;

            int tempCoefficient = previousCoefficient;
            previousCoefficient = currentCoefficient - quotient * previousCoefficient;
            currentCoefficient = tempCoefficient;
        }

        if (currentCoefficient < 0) {
            currentCoefficient += originalModulus;
        }

        return currentCoefficient;
    }
}