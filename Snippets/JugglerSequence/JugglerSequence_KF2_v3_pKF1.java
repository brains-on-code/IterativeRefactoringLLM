package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class JugglerSequence {

    private JugglerSequence() {
    }

    public static void printJugglerSequence(int startNumber) {
        int currentNumber = startNumber;
        List<String> sequence = new ArrayList<>();
        sequence.add(Integer.toString(currentNumber));

        while (currentNumber != 1) {
            int nextNumber;

            if (currentNumber % 2 == 0) {
                nextNumber = (int) Math.floor(Math.sqrt(currentNumber));
            } else {
                double squareRoot = Math.sqrt(currentNumber);
                nextNumber = (int) Math.floor(squareRoot * squareRoot * squareRoot);
            }

            currentNumber = nextNumber;
            sequence.add(Integer.toString(currentNumber));
        }

        String sequenceOutput = String.join(",", sequence);
        System.out.println(sequenceOutput);
    }

    public static void main(String[] args) {
        printJugglerSequence(3);
    }
}