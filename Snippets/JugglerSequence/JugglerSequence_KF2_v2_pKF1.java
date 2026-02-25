package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class JugglerSequence {

    private JugglerSequence() {
    }

    public static void printJugglerSequence(int initialValue) {
        int currentValue = initialValue;
        List<String> sequenceValues = new ArrayList<>();
        sequenceValues.add(Integer.toString(currentValue));

        while (currentValue != 1) {
            int nextValue;

            if (currentValue % 2 == 0) {
                nextValue = (int) Math.floor(Math.sqrt(currentValue));
            } else {
                double squareRoot = Math.sqrt(currentValue);
                nextValue = (int) Math.floor(squareRoot * squareRoot * squareRoot);
            }

            currentValue = nextValue;
            sequenceValues.add(Integer.toString(currentValue));
        }

        String sequenceAsString = String.join(",", sequenceValues);
        System.out.println(sequenceAsString);
    }

    public static void main(String[] args) {
        printJugglerSequence(3);
    }
}