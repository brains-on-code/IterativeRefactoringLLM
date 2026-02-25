package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

/*
 * Java program for printing juggler sequence
 * Wikipedia: https://en.wikipedia.org/wiki/Juggler_sequence
 *
 * Author: Akshay Dubey (https://github.com/itsAkshayDubey)
 *
 */

public final class JugglerSequence {

    private JugglerSequence() {
        // Utility class; prevent instantiation
    }

    /**
     * Prints the Juggler sequence starting from the given number.
     *
     * @param startNumber the starting number of the Juggler sequence
     */
    public static void jugglerSequence(int startNumber) {
        int current = startNumber;
        List<String> sequence = new ArrayList<>();
        sequence.add(Integer.toString(current));

        while (current != 1) {
            current = nextJugglerTerm(current);
            sequence.add(Integer.toString(current));
        }

        String result = String.join(",", sequence);
        System.out.println(result);
    }

    /**
     * Computes the next term in the Juggler sequence.
     *
     * @param n the current term
     * @return the next term in the sequence
     */
    private static int nextJugglerTerm(int n) {
        double root = Math.sqrt(n);
        if (n % 2 == 0) {
            return (int) Math.floor(root);
        } else {
            return (int) Math.floor(root * root * root);
        }
    }

    public static void main(String[] args) {
        jugglerSequence(3);
        // Output: 3,5,11,36,6,2,1
    }
}