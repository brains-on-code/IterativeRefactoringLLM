package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating and printing Juggler sequences.
 *
 * <p>Wikipedia: https://en.wikipedia.org/wiki/Juggler_sequence
 *
 * <p>Author: Akshay Dubey (https://github.com/itsAkshayDubey)
 */
public final class JugglerSequence {

    private JugglerSequence() {
        // Prevent instantiation
    }

    /**
     * Prints the Juggler sequence starting from the given number.
     *
     * <p>For a term {@code n} in the sequence:
     * <ul>
     *   <li>If {@code n} is even, the next term is {@code floor(sqrt(n))}.</li>
     *   <li>If {@code n} is odd, the next term is {@code floor(n^(3/2))}.</li>
     * </ul>
     *
     * @param start the starting number of the Juggler sequence
     * @throws IllegalArgumentException if {@code start} is not positive
     */
    public static void jugglerSequence(int start) {
        if (start <= 0) {
            throw new IllegalArgumentException("Start value must be positive");
        }

        int current = start;
        List<String> sequence = new ArrayList<>();
        sequence.add(Integer.toString(current));

        while (current != 1) {
            current = nextTerm(current);
            sequence.add(Integer.toString(current));
        }

        System.out.println(String.join(",", sequence));
    }

    /**
     * Computes the next term in the Juggler sequence for a given term.
     *
     * @param n the current term (must be positive)
     * @return the next term in the sequence
     */
    private static int nextTerm(int n) {
        if (n % 2 == 0) {
            return (int) Math.floor(Math.sqrt(n));
        }

        double root = Math.sqrt(n);
        // n^(3/2) = (sqrt(n))^3
        return (int) Math.floor(root * root * root);
    }

    public static void main(String[] args) {
        // Expected output: 3,5,11,36,6,2,1
        jugglerSequence(3);
    }
}