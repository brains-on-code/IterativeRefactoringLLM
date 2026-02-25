package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

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
        int current = start;
        List<String> sequence = new ArrayList<>();
        sequence.add(Integer.toString(current));

        while (current != 1) {
            int next;
            double sqrt = Math.sqrt(current);

            if (current % 2 == 0) {
                next = (int) Math.floor(sqrt);
            } else {
                next = (int) Math.floor(sqrt * sqrt * sqrt);
            }

            current = next;
            sequence.add(Integer.toString(current));
        }

        String output = String.join(",", sequence);
        System.out.println(output);
    }

    public static void main(String[] args) {
        generateSequence(3);
        // expecting: 3,5,11,36,6,2,1
    }
}