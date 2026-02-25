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
     * <p>Definition:
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
            current = isEven(current) ? nextEvenTerm(current) : nextOddTerm(current);
            sequence.add(Integer.toString(current));
        }

        System.out.println(String.join(",", sequence));
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }

    private static int nextEvenTerm(int number) {
        return (int) Math.sqrt(number);
    }

    private static int nextOddTerm(int number) {
        double sqrt = Math.sqrt(number);
        return (int) Math.floor(sqrt * sqrt * sqrt);
    }

    public static void main(String[] args) {
        jugglerSequence(3);
    }
}