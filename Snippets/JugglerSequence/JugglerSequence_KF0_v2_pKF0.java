package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

/**
 * Java program for printing Juggler sequence.
 * Wikipedia: https://en.wikipedia.org/wiki/Juggler_sequence
 *
 * Author: Akshay Dubey (https://github.com/itsAkshayDubey)
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
        int currentTerm = startNumber;
        List<String> sequence = new ArrayList<>();
        sequence.add(Integer.toString(currentTerm));

        while (currentTerm != 1) {
            currentTerm = nextJugglerTerm(currentTerm);
            sequence.add(Integer.toString(currentTerm));
        }

        System.out.println(String.join(",", sequence));
    }

    /**
     * Computes the next term in the Juggler sequence.
     *
     * @param currentTerm the current term
     * @return the next term in the sequence
     */
    private static int nextJugglerTerm(int currentTerm) {
        double root = Math.sqrt(currentTerm);
        return (currentTerm % 2 == 0)
            ? (int) Math.floor(root)
            : (int) Math.floor(root * root * root);
    }

    public static void main(String[] args) {
        jugglerSequence(3);
        // Output: 3,5,11,36,6,2,1
    }
}