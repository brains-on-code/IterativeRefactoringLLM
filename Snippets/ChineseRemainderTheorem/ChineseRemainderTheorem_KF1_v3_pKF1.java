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
            int modulus = moduli.get(i);
            int remainder = remainders.get(i);

            int partialProduct = productOfModuli / modulus;
            int inverse = modularInverse(partialProduct, modulus);

            result += remainder * partialProduct * inverse;
        }

        result %= productOfModuli;
        if (result < 0) {
            result += productOfModuli;
        }

        return result;
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

        int dividend = value;
        int divisor = modulus;

        while (dividend > 1) {
            int quotient = dividend / divisor;
            int tempDivisor = divisor;

            divisor = dividend % divisor;
            dividend = tempDivisor;

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