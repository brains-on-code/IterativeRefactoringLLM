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
        int currentValue = startNumber;
        List<String> sequenceValues = new ArrayList<>();
        sequenceValues.add(Integer.toString(currentValue));

        while (currentValue != 1) {
            int nextValue;
            if (currentValue % 2 == 0) {
                nextValue = (int) Math.floor(Math.sqrt(currentValue));
            } else {
                double currentSquareRoot = Math.sqrt(currentValue);
                nextValue = (int) Math.floor(
                    currentSquareRoot * currentSquareRoot * currentSquareRoot
                );
            }
            currentValue = nextValue;
            sequenceValues.add(Integer.toString(currentValue));
        }

        String sequenceAsString = String.join(",", sequenceValues);
        System.out.println(sequenceAsString);
    }

    public static void main(String[] args) {
        printJugglerSequence(3);
        // Output: 3,5,11,36,6,2,1
    }
}