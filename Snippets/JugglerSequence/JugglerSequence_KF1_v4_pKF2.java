package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates and prints an integer sequence.
 *
 * <p>Sequence rules:
 * <ul>
 *     <li>Start from a positive integer {@code start}.</li>
 *     <li>For each term {@code n}:
 *         <ul>
 *             <li>If {@code n} is even, the next term is {@code floor(sqrt(n))}.</li>
 *             <li>If {@code n} is odd, the next term is {@code floor(sqrt(n)^3)}.</li>
 *         </ul>
 *     </li>
 *     <li>The sequence ends when it reaches {@code 1} (inclusive).</li>
 * </ul>
 */
public final class IntegerSequenceGenerator {

    private IntegerSequenceGenerator() {
        // Prevent instantiation
    }

    /**
     * Generates the sequence starting from {@code start} and prints it as a
     * comma-separated list to standard output.
     *
     * @param start the starting integer for the sequence (must be >= 1)
     * @throws IllegalArgumentException if {@code start} is less than 1
     */
    public static void printSequence(int start) {
        List<Integer> sequence = generateSequence(start);
        String result = sequence.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        System.out.println(result);
    }

    /**
     * Generates the sequence starting from {@code start}.
     *
     * @param start the starting integer for the sequence (must be >= 1)
     * @return a list containing the sequence, including the starting value and the final 1
     * @throws IllegalArgumentException if {@code start} is less than 1
     */
    public static List<Integer> generateSequence(int start) {
        if (start < 1) {
            throw new IllegalArgumentException("Start value must be >= 1");
        }

        List<Integer> sequence = new ArrayList<>();
        int current = start;
        sequence.add(current);

        while (current != 1) {
            current = nextValue(current);
            sequence.add(current);
        }

        return sequence;
    }

    /**
     * Computes the next value in the sequence based on the current value.
     *
     * @param current the current value in the sequence
     * @return the next value in the sequence
     */
    private static int nextValue(int current) {
        if (isEven(current)) {
            return (int) Math.sqrt(current);
        }

        double sqrt = Math.sqrt(current);
        return (int) Math.floor(sqrt * sqrt * sqrt);
    }

    /**
     * Checks whether the given value is even.
     *
     * @param value the integer to test
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
    public static void main(String[] args) {
        printSequence(3);
        // Expected output: 3,5,11,36,6,2,1
    }
}