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
     */
    public static void jugglerSequence(int start) {
        int n = start;
        List<String> sequence = new ArrayList<>();
        sequence.add(Integer.toString(n));

        while (n != 1) {
            if (n % 2 == 0) {
                n = (int) Math.floor(Math.sqrt(n));
            } else {
                double root = Math.sqrt(n);
                n = (int) Math.floor(root * root * root); // n^(3/2)
            }
            sequence.add(Integer.toString(n));
        }

        String result = String.join(",", sequence);
        System.out.println(result);
    }

    public static void main(String[] args) {
        jugglerSequence(3); // Output: 3,5,11,36,6,2,1
    }
}