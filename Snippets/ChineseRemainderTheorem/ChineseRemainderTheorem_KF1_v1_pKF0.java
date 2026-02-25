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

        // Compute the product of all moduli
        for (int modulus : moduli) {
            productOfModuli *= modulus;
        }

        // Apply the CRT construction
        for (int i = 0; i < moduli.size(); i++) {
            int partialProduct = productOfModuli / moduli.get(i);
            int inverse = method2(partialProduct, moduli.get(i));
            result += residues.get(i) * partialProduct * inverse;
        }

        // Normalize result to be within [0, productOfModuli)
        result %= productOfModuli;
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
     *   (number * x) ≡ 1 (mod modulus)
     *
     * @param number  the number whose inverse is to be found
     * @param modulus the modulus
     * @return the modular inverse of number modulo modulus
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

        while (a > 1) {
            int quotient = a / b;
            int temp = b;

            b = a % b;
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