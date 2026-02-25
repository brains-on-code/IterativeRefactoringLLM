package com.thealgorithms.maths;

import java.util.List;

/**
 * Implementation of the Chinese Remainder Theorem (CRT) algorithm.
 *
 * <p>The Chinese Remainder Theorem (CRT) is used to solve systems of simultaneous
 * congruences. Given several pairwise coprime moduli and corresponding remainders,
 * the algorithm finds the smallest positive solution.</p>
 */
public final class ChineseRemainderTheorem {

    private ChineseRemainderTheorem() {
        // Utility class; prevent instantiation
    }

    /**
     * Solves the Chinese Remainder Theorem problem.
     *
     * @param remainders the list of remainders
     * @param moduli     the list of pairwise coprime moduli
     * @return the smallest positive solution that satisfies all the given congruences
     * @throws IllegalArgumentException if input lists are null, differ in size, or are empty
     */
    public static int solveCRT(List<Integer> remainders, List<Integer> moduli) {
        validateInput(remainders, moduli);

        final int product = computeProduct(moduli);
        int result = 0;

        for (int i = 0; i < moduli.size(); i++) {
            final int modulus = moduli.get(i);
            final int remainder = remainders.get(i);

            final int partialProduct = product / modulus;
            final int inverse = modInverse(partialProduct, modulus);

            result += remainder * partialProduct * inverse;
        }

        result %= product;
        return result < 0 ? result + product : result;
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

    private static int computeProduct(List<Integer> moduli) {
        int product = 1;
        for (int modulus : moduli) {
            product *= modulus;
        }
        return product;
    }

    /**
     * Computes the modular inverse of a number with respect to a modulus using
     * the Extended Euclidean Algorithm.
     *
     * @param a the number for which to find the inverse
     * @param m the modulus
     * @return the modular inverse of {@code a} modulo {@code m}
     */
    private static int modInverse(int a, int m) {
        if (m == 1) {
            return 0;
        }

        final int originalModulus = m;
        int x0 = 0;
        int x1 = 1;
        int value = a;

        while (value > 1) {
            final int quotient = value / m;

            int temp = m;
            m = value % m;
            value = temp;

            temp = x0;
            x0 = x1 - quotient * x0;
            x1 = temp;
        }

        if (x1 < 0) {
            x1 += originalModulus;
        }

        return x1;
    }
}