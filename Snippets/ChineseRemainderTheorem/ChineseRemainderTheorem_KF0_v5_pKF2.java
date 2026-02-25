package com.thealgorithms.maths;

import java.util.List;

/**
 * Utility class for solving systems of congruences using the Chinese Remainder Theorem (CRT).
 *
 * <p>Given pairwise coprime moduli and corresponding remainders, this class
 * computes the smallest positive integer that satisfies all congruences.</p>
 */
public final class ChineseRemainderTheorem {

    private ChineseRemainderTheorem() {
        // Utility class; prevent instantiation
    }

    /**
     * Solves a system of congruences using the Chinese Remainder Theorem.
     *
     * <p>Given lists {@code remainders} and {@code moduli}, this method finds the smallest
     * positive integer {@code x} such that for every index {@code i}:</p>
     *
     * <pre>
     *   x ≡ remainders.get(i) (mod moduli.get(i))
     * </pre>
     *
     * <p>All values in {@code moduli} must be pairwise coprime.</p>
     *
     * @param remainders list of remainders
     * @param moduli list of pairwise coprime moduli
     * @return the smallest positive integer satisfying all congruences
     * @throws IllegalArgumentException if the input lists are null or have different sizes
     */
    public static int solveCRT(List<Integer> remainders, List<Integer> moduli) {
        validateInput(remainders, moduli);

        int productOfModuli = computeProduct(moduli);

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
        if (remainders == null || moduli == null || remainders.size() != moduli.size()) {
            throw new IllegalArgumentException(
                "Remainders and moduli must be non-null and of equal size."
            );
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
     * Computes the modular inverse of {@code a} modulo {@code m} using the Extended Euclidean Algorithm.
     *
     * <p>Finds an integer {@code x} such that:</p>
     *
     * <pre>
     *   (a * x) ≡ 1 (mod m)
     * </pre>
     *
     * <p>Assumes that {@code a} and {@code m} are coprime.</p>
     *
     * @param a the value whose modular inverse is to be computed
     * @param m the modulus
     * @return the modular inverse of {@code a} modulo {@code m}
     * @throws ArithmeticException if the modular inverse does not exist
     */
    private static int modInverse(int a, int m) {
        if (m == 1) {
            return 0;
        }

        int originalModulus = m;
        int x0 = 0;
        int x1 = 1;
        int value = a;

        while (value > 1) {
            int quotient = value / m;

            int previousM = m;
            m = value % m;
            value = previousM;

            int previousX0 = x0;
            x0 = x1 - quotient * x0;
            x1 = previousX0;
        }

        if (value != 1) {
            throw new ArithmeticException(
                "Modular inverse does not exist for a = " + a + ", m = " + originalModulus
            );
        }

        if (x1 < 0) {
            x1 += originalModulus;
        }

        return x1;
    }
}