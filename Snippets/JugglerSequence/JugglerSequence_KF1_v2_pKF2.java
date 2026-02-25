package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating and printing a specific integer sequence.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Generates and prints a sequence of integers starting from {@code start}.
     * <p>
     * For each term {@code n} in the sequence, the next term is:
     * <ul>
     *     <li>{@code floor(sqrt(n))} if {@code n} is even</li>
     *     <li>{@code floor(sqrt(n)^3)} if {@code n} is odd</li>
     * </ul>
     * The sequence terminates when the value reaches {@code 1} (inclusive).
     *
     * @param start the starting integer for the sequence
     */
    public static void method1(int start) {
        int current = start;
        List<String> sequence = new ArrayList<>();
        sequence.add(Integer.toString(current));

        while (current != 1) {
            int next = getNextValue(current);
            current = next;
            sequence.add(Integer.toString(current));
        }

        String result = String.join(",", sequence);
        System.out.println(result);
    }

    /**
     * Computes the next value in the sequence based on the current value.
     *
     * @param current the current value in the sequence
     * @return the next value in the sequence
     */
    private static int getNextValue(int current) {
        if (isEven(current)) {
            return (int) Math.floor(Math.sqrt(current));
        } else {
            double sqrt = Math.sqrt(current);
            return (int) Math.floor(sqrt * sqrt * sqrt);
        }
    }

    /**
     * Checks whether a given integer is even.
     *
     * @param value the integer to check
     * @return {@code true} if {@code value} is even; {@code false} otherwise
     */
    private static boolean isEven(int value) {
        return value % 2 == 0;
    }

    /**
     * Example entry point.
     *
     * @param args command-line arguments (unused)
     */
    public static void method2(String[] args) {
        method1(3);
        // Expected output: 3,5,11,36,6,2,1
    }
}