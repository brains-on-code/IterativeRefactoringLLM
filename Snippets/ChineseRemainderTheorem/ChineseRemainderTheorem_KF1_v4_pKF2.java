package com.thealgorithms.maths;

import java.util.List;

/**
 * Utility class for operations related to the Chinese Remainder Theorem (CRT).
 */
public final class ChineseRemainderTheorem {

    private ChineseRemainderTheorem() {
        // Utility class; prevent instantiation.
    }

    /**
     * Solves a system of congruences using the Chinese Remainder Theorem.
     *
     * Given congruences of the form:
     *   x ≡ residues[i] (mod moduli[i]) for all i,
     * this method returns the smallest non-negative solution x.
     *
     * @param residues list of remainders (a_i)
     * @param moduli   list of pairwise coprime moduli (m_i)
     * @return the smallest non-negative solution x to the system of congruences
     */
    public static int solve(List<Integer> residues, List<Integer> moduli) {
        int modulusProduct = 1;
        int result = 0;

        // Compute the product of all moduli: M = m1 * m2 * ... * mn
        for (int modulus : moduli) {
            modulusProduct *= modulus;
        }

        /*
         * Construct the solution:
         *
         *   x = Σ (a_i * M_i * inv(M_i, m_i))  (mod M),
         *
         * where:
         *   - M   = product of all moduli
         *   - M_i = M / m_i
         *   - inv(M_i, m_i) is the modular inverse of M_i modulo m_i
         */
        for (int i = 0; i < moduli.size(); i++) {
            int currentModulus = moduli.get(i);
            int residue = residues.get(i);

            int partialProduct = modulusProduct / currentModulus; // M_i
            int inverse = modularInverse(partialProduct, currentModulus);

            result += residue * partialProduct * inverse;
        }

        // Normalize result to the range [0, M)
        result %= modulusProduct;
        if (result < 0) {
            result += modulusProduct;
        }

        return result;
    }

    /**
     * Computes the modular multiplicative inverse of a number using the
     * Extended Euclidean Algorithm.
     *
     * Finds x such that:
     *   (number * x) ≡ 1 (mod modulus),
     * assuming gcd(number, modulus) = 1.
     *
     * @param number  the value whose inverse is to be found
     * @param modulus the modulus
     * @return the modular inverse of {@code number} modulo {@code modulus}
     */
    private static int modularInverse(int number, int modulus) {
        if (modulus == 1) {
            return 0;
        }

        int originalModulus = modulus;
        int a = number;
        int b = modulus;

        // Coefficients for the Extended Euclidean Algorithm
        int prevCoefficient = 0;
        int currCoefficient = 1;

        // Extended Euclidean Algorithm
        while (a > 1) {
            int quotient = a / b;

            int temp = b;
            b = a % b;
            a = temp;

            temp = prevCoefficient;
            prevCoefficient = currCoefficient - quotient * prevCoefficient;
            currCoefficient = temp;
        }

        // Ensure the result is in [0, originalModulus)
        if (currCoefficient < 0) {
            currCoefficient += originalModulus;
        }

        return currCoefficient;
    }
}