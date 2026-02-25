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
    }

    /**
     * Prints the Juggler sequence starting from the given number.
     *
     * @param startNumber number from which the Juggler sequence starts
     */
    public static void printJugglerSequence(int startNumber) {
        int currentTerm = startNumber;
        List<String> sequenceTerms = new ArrayList<>();
        sequenceTerms.add(Integer.toString(currentTerm));

        while (currentTerm != 1) {
            int nextTerm;
            if (currentTerm % 2 == 0) {
                nextTerm = (int) Math.floor(Math.sqrt(currentTerm));
            } else {
                double squareRoot = Math.sqrt(currentTerm);
                nextTerm = (int) Math.floor(squareRoot * squareRoot * squareRoot);
            }
            currentTerm = nextTerm;
            sequenceTerms.add(Integer.toString(currentTerm));
        }

        String sequenceAsString = String.join(",", sequenceTerms);
        System.out.println(sequenceAsString);
    }

    public static void main(String[] args) {
        printJugglerSequence(3);
        // Output: 3,5,11,36,6,2,1
    }
}