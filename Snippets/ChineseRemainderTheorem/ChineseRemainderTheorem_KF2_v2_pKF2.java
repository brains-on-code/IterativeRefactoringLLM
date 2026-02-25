package com.thealgorithms.maths;

import java.util.List;

public final class ChineseRemainderTheorem {

    private ChineseRemainderTheorem() {
        // Prevent instantiation of utility class
    }

    /**
     * Solves a system of congruences using the Chinese Remainder Theorem.
     *
     * Given:
     *   x ≡ remainders[i] (mod moduli[i]) for all i,
     * this method returns the smallest non-negative solution x.
     *
     * @param remainders list of remainders
     * @param moduli     list of pairwise coprime moduli
     * @return smallest non-negative solution to the system of congruences
     * @throws IllegalArgumentException if input lists are null, sizes differ, or are empty
     */
    public static int solveCRT(List<Integer> remainders, List<Integer> moduli) {
        validateInput(remainders, moduli);

        int productOfModuli = 1;
        for (int modulus : moduli) {
            productOfModuli *= modulus;
        }

        int result = 0;
        for (int i = 0; i < moduli.size(); i++) {
            int modulus = moduli.get(i);
            int remainder = remainders.get(i);

            int partialProduct = productOfModuli / modulus;
            int inverse = modInverse(partialProduct, modulus);

            result += remainder * partialProduct * inverse;
        }

        result %= productOfModuli;
        if (result < 0) {
            result += productOfModuli;
        }

        return result;
    }

    private static void validateInput(List<Integer> remainders, List<Integer> moduli) {
        if (remainders == null || moduli == null) {
            throw new IllegalArgumentException("Remainders and moduli must not be null.");
        }
        if (remainders.size() != moduli.size()) {
            throw new IllegalArgumentException("Remainders and moduli must have the same size.");
        }
        if (remainders.isEmpty()) {
            throw new IllegalArgumentException("Remainders and moduli must not be empty.");
        }
    }

    /**
     * Computes the modular multiplicative inverse of a modulo m using the
     * Extended Euclidean Algorithm.
     *
     * Finds x such that: a * x ≡ 1 (mod m).
     *
     * @param a value whose inverse is to be found
     * @param m modulus
     * @return modular inverse of a modulo m
     * @throws ArithmeticException if the inverse does not exist
     */
    private static int modInverse(int a, int m) {
        if (m == 1) {
            return 0;
        }

        int originalModulus = m;
        int xPrev = 0;
        int xCurr = 1;

        int value = a;
        int modulus = m;

        while (value > 1) {
            int quotient = value / modulus;

            int temp = modulus;
            modulus = value % modulus;
            value = temp;

            temp = xPrev;
            xPrev = xCurr - quotient * xPrev;
            xCurr = temp;
        }

        if (value != 1) {
            throw new ArithmeticException(
                "Modular inverse does not exist for a = " + a + ", m = " + originalModulus
            );
        }

        if (xCurr < 0) {
            xCurr += originalModulus;
        }

        return xCurr;
    }
}