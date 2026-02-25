package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class NumberSequenceGenerator {

    private NumberSequenceGenerator() {
    }

    /**
     * Generates and prints a sequence of numbers starting from the given initial value.
     * For each step:
     * - If the current number is even, the next number is floor(sqrt(current)).
     * - If the current number is odd, the next number is floor(cuberoot(current)),
     *   implemented as floor(sqrt(n) * sqrt(n) * sqrt(n)).
     * The sequence ends when the value reaches 1.
     *
     * @param initialValue the starting integer for the sequence
     */
    public static void generateSequence(int initialValue) {
        int currentValue = initialValue;
        List<String> sequence = new ArrayList<>();
        sequence.add(Integer.toString(currentValue));

        while (currentValue != 1) {
            int nextValue;
            double sqrt = Math.sqrt(currentValue);

            if (currentValue % 2 == 0) {
                nextValue = (int) Math.floor(sqrt);
            } else {
                nextValue = (int) Math.floor(sqrt * sqrt * sqrt);
            }

            currentValue = nextValue;
            sequence.add(Integer.toString(currentValue));
        }

        String output = String.join(",", sequence);
        System.out.println(output);
    }

    public static void main(String[] args) {
        generateSequence(3);
        // expecting: 3,5,11,36,6,2,1
    }
}