package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class JugglerSequence {

    private JugglerSequence() {
        // Prevent instantiation
    }

    /**
     * Generates and prints the Juggler sequence starting from the given number.
     *
     * <p>The Juggler sequence is defined as:
     * <ul>
     *   <li>a(0) = inputNumber</li>
     *   <li>a(n+1) = floor(sqrt(a(n))) if a(n) is even</li>
     *   <li>a(n+1) = floor(a(n)^(3/2)) if a(n) is odd</li>
     * </ul>
     *
     * @param inputNumber the starting number of the sequence
     */
    public static void jugglerSequence(int inputNumber) {
        int current = inputNumber;
        List<String> sequence = new ArrayList<>();
        sequence.add(Integer.toString(current));

        while (current != 1) {
            if (current % 2 == 0) {
                current = (int) Math.floor(Math.sqrt(current));
            } else {
                double sqrt = Math.sqrt(current);
                current = (int) Math.floor(sqrt * sqrt * sqrt);
            }
            sequence.add(Integer.toString(current));
        }

        String result = String.join(",", sequence);
        System.out.println(result);
    }

    public static void main(String[] args) {
        jugglerSequence(3);
    }
}