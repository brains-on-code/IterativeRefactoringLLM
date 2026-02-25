package com.thealgorithms.maths;

import java.util.List;

/**
 * Implementation of the Chinese Remainder Theorem (CRT).
 *
 * <p>The Chinese Remainder Theorem is used to solve systems of simultaneous
 * congruences. Given several pairwise coprime moduli and corresponding
 * remainders, this class provides a method to find the smallest positive
 * solution.</p>
 */
public final class ChineseRemainderTheorem {

    private ChineseRemainderTheorem() {
        // Utility class; prevent instantiation
    }

    /**
     * Solves a system of congruences using the Chinese Remainder Theorem.
     *
     * <p>Given:
     * <pre>
     *   x ≡ remainders[i] (mod moduli[i])  for all i
     * </pre>
     * where all {@code moduli[i]} are pairwise coprime, this method returns
     * the smallest positive integer {@code x} that satisfies all congruences.</p>
     *
     * @param remainders list of remainders
     * @param moduli list of pairwise coprime moduli
     * @return the smallest positive solution satisfying all congruences
     * @throws IllegalArgumentException if the input lists are null or have different sizes
     */
    public static int solveCRT(List<Integer> remainders, List<Integer> moduli) {
        if (remainders == null || moduli == null || remainders.size() != moduli.size()) {
            throw new IllegalArgumentException("Remainders and moduli must be non-null and of equal size.");
        }

        int product = 1;
        int result = 0;

        // Compute the product of all moduli
        for (int mod : moduli) {
            product *= mod;
        }

        // For each congruence: x ≡ remainders[i] (mod moduli[i])
        for (int i = 0; i < moduli.size(); i++) {
            int modulus = moduli.get(i);
            int remainder = remainders.get(i);

            int partialProduct = product / modulus;
            int inverse = modInverse(partialProduct, modulus);

            result += remainder * partialProduct * inverse;
        }

        // Normalize result to the smallest positive solution
        result %= product;
        if (result < 0) {
            result += product;
        }

        return result;
    }

    /**
     * Computes the modular inverse of {@code a} modulo {@code m} using
     * the Extended Euclidean Algorithm.
     *
     * <p>Returns {@code x} such that:
     * <pre>
     *   (a * x) ≡ 1 (mod m)
     * </pre>
     * assuming that {@code a} and {@code m} are coprime.</p>
     *
     * @param a the number whose modular inverse is to be computed
     * @param m the modulus
     * @return the modular inverse of {@code a} modulo {@code m}
     * @throws ArithmeticException if the modular inverse does not exist
     */
    private static int modInverse(int a, int m) {
        int originalModulus = m;
        int x0 = 0;
        int x1 = 1;

        if (m == 1) {
            return 0;
        }

        int value = a;
        while (value > 1) {
            int quotient = value / m;
            int temp = m;

            // Apply Euclid's algorithm step
            m = value % m;
            value = temp;

            temp = x0;
            x0 = x1 - quotient * x0;
            x1 = temp;
        }

        // If gcd(a, originalModulus) != 1, inverse does not exist
        if (value != 1) {
            throw new ArithmeticException("Modular inverse does not exist for a = " + a + ", m = " + originalModulus);
        }

        // Ensure x1 is positive
        if (x1 < 0) {
            x1 += originalModulus;
        }

        return x1;
    }
}