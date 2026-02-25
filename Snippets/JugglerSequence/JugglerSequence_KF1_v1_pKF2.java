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
     *     <li>floor(sqrt(n)) if n is even</li>
     *     <li>floor(sqrt(n)^3) if n is odd</li>
     * </ul>
     * The sequence ends when the value reaches 1 (inclusive).
     *
     * @param start the starting integer for the sequence
     */
    public static void method1(int start) {
        int current = start;
        List<String> sequence = new ArrayList<>();
        sequence.add(Integer.toString(current));

        while (current != 1) {
            int next;
            if (current % 2 == 0) {
                next = (int) Math.floor(Math.sqrt(current));
            } else {
                double sqrt = Math.sqrt(current);
                next = (int) Math.floor(sqrt * sqrt * sqrt);
            }
            current = next;
            sequence.add(Integer.toString(current));
        }

        String result = String.join(",", sequence);
        System.out.println(result);
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