package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class JugglerSequence {

    private JugglerSequence() {
        // Utility class; prevent instantiation
    }

    public static void jugglerSequence(int start) {
        int currentTerm = start;
        List<String> sequence = new ArrayList<>();
        sequence.add(String.valueOf(currentTerm));

        while (currentTerm != 1) {
            currentTerm = calculateNextTerm(currentTerm);
            sequence.add(String.valueOf(currentTerm));
        }

        System.out.println(String.join(",", sequence));
    }

    private static int calculateNextTerm(int term) {
        if (isEven(term)) {
            return (int) Math.sqrt(term);
        }

        double sqrt = Math.sqrt(term);
        return (int) Math.pow(sqrt, 3);
    }

    private static boolean isEven(int number) {
        return (number & 1) == 0;
    }

    public static void main(String[] args) {
        jugglerSequence(3);
    }
}