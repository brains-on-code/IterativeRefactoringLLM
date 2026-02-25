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
        int productOfModuli = 1;
        int result = 0;

        for (int modulus : moduli) {
            productOfModuli *= modulus;
        }

        for (int i = 0; i < moduli.size(); i++) {
            int partialProduct = productOfModuli / moduli.get(i);
            int inverse = modularInverse(partialProduct, moduli.get(i));
            result += remainders.get(i) * partialProduct * inverse;
        }

        result = result % productOfModuli;
        if (result < 0) {
            result += productOfModuli;
        }

        return result;
    }

    /**
     * Computes the modular multiplicative inverse of a modulo m using the Extended Euclidean Algorithm.
     *
     * @param a the number to invert
     * @param m the modulus
     * @return the modular inverse of a modulo m
     */
    private static int modularInverse(int a, int m) {
        int originalModulus = m;
        int previousCoefficient = 0;
        int currentCoefficient = 1;

        if (m == 1) {
            return 0;
        }

        while (a > 1) {
            int quotient = a / m;
            int temp = m;

            m = a % m;
            a = temp;
            temp = previousCoefficient;

            previousCoefficient = currentCoefficient - quotient * previousCoefficient;
            currentCoefficient = temp;
        }

        if (currentCoefficient < 0) {
            currentCoefficient += originalModulus;
        }

        return currentCoefficient;
    }
}