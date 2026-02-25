package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates and prints a sequence of integers starting from {@code start},
     * where each next value is:
     * <ul>
     *   <li>floor(sqrt(current)) if current is even</li>
     *   <li>floor(sqrt(current)^3) if current is odd</li>
     * </ul>
     * The sequence ends when the value reaches 1 (inclusive).
     *
     * @param start the starting integer for the sequence
     */
    public static void generateSequence(int start) {
        List<Integer> sequence = buildSequence(start);
        printSequence(sequence);
    }

    private static List<Integer> buildSequence(int start) {
        List<Integer> sequence = new ArrayList<>();
        int current = start;
        sequence.add(current);

        while (current != 1) {
            current = nextValue(current);
            sequence.add(current);
        }

        return sequence;
    }

    private static int nextValue(int current) {
        double sqrt = Math.sqrt(current);
        double next = isEven(current) ? sqrt : sqrt * sqrt * sqrt;
        return (int) Math.floor(next);
    }

    private static boolean isEven(int value) {
        return (value & 1) == 0;
    }

    private static void printSequence(List<Integer> sequence) {
        String output = sequence.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(","));
        System.out.println(output);
    }

    public static void main(String[] args) {
        generateSequence(3);
        // expecting: 3,5,11,36,6,2,1
    }
}