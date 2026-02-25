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
        int currentNumber = initialValue;
        List<String> sequenceValues = new ArrayList<>();
        sequenceValues.add(Integer.toString(currentNumber));

        while (currentNumber != 1) {
            double squareRootOfCurrent = Math.sqrt(currentNumber);
            boolean isEven = currentNumber % 2 == 0;

            int nextNumber = isEven
                ? (int) Math.floor(squareRootOfCurrent)
                : (int) Math.floor(
                    squareRootOfCurrent
                        * squareRootOfCurrent
                        * squareRootOfCurrent
                );

            currentNumber = nextNumber;
            sequenceValues.add(Integer.toString(currentNumber));
        }

        String sequenceOutput = String.join(",", sequenceValues);
        System.out.println(sequenceOutput);
    }

    public static void main(String[] args) {
        generateSequence(3);
        // expecting: 3,5,11,36,6,2,1
    }
}