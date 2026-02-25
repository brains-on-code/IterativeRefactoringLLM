package com.thealgorithms.maths;

import java.util.List;

/**
 * Utility class for operations related to the Chinese Remainder Theorem (CRT).
 */
public final class ChineseRemainderTheorem {

    private ChineseRemainderTheorem() {
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
    public static int solve(List<Integer> residues, List<Integer> moduli) {
        int productOfModuli = calculateProductOfModuli(moduli);
        int result = 0;

        for (int i = 0; i < moduli.size(); i++) {
            int modulus = moduli.get(i);
            int residue = residues.get(i);

            int partialProduct = productOfModuli / modulus;
            int inverse = modularInverse(partialProduct, modulus);

            result += residue * partialProduct * inverse;
        }

        result %= productOfModuli;
        if (result < 0) {
            result += productOfModuli;
        }

        return result;
    }

    private static int calculateProductOfModuli(List<Integer> moduli) {
        int product = 1;
        for (int modulus : moduli) {
            product *= modulus;
        }
        return product;
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
    private static int modularInverse(int number, int modulus) {
        if (modulus == 1) {
            return 0;
        }

        int originalModulus = modulus;
        int previousCoefficient = 0;
        int currentCoefficient = 1;

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