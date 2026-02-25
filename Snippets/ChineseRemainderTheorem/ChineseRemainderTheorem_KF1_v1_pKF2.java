package com.thealgorithms.maths;

import java.util.List;

/**
 * Utility class for operations related to the Chinese Remainder Theorem (CRT).
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Solves a system of congruences using the Chinese Remainder Theorem.
     *
     * Given:
     *   x ≡ residues[i] (mod moduli[i]) for all i,
     * this method returns the smallest non-negative solution x.
     *
     * @param residues list of remainders (a_i)
     * @param moduli   list of pairwise coprime moduli (m_i)
     * @return the smallest non-negative solution x to the system of congruences
     */
    public static int method1(List<Integer> residues, List<Integer> moduli) {
        int productOfModuli = 1;
        int result = 0;

        // Compute the product of all moduli: M = m1 * m2 * ... * mn
        for (int modulus : moduli) {
            productOfModuli *= modulus;
        }

        // Apply the CRT construction:
        // x = Σ (a_i * M_i * inv(M_i, m_i))  (mod M),
        // where M_i = M / m_i and inv(M_i, m_i) is the modular inverse of M_i modulo m_i.
        for (int i = 0; i < moduli.size(); i++) {
            int partialProduct = productOfModuli / moduli.get(i);
            int inverse = method2(partialProduct, moduli.get(i));
            result += residues.get(i) * partialProduct * inverse;
        }

        // Normalize result to the range [0, M)
        result = result % productOfModuli;
        if (result < 0) {
            result += productOfModuli;
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
    private static int method2(int number, int modulus) {
        int originalModulus = modulus;
        int previousCoefficient = 0;
        int currentCoefficient = 1;

        if (modulus == 1) {
            return 0;
        }

        int a = number;
        int b = modulus;

        // Extended Euclidean Algorithm to find the inverse
        while (a > 1) {
            int quotient = a / b;
            int temp = b;

            b = a % b;
            a = temp;
            temp = previousCoefficient;

            previousCoefficient = currentCoefficient - quotient * previousCoefficient;
            currentCoefficient = temp;
        }

        // Ensure the result is positive
        if (currentCoefficient < 0) {
            currentCoefficient += originalModulus;
        }

        return currentCoefficient;
    }
}