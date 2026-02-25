package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class JugglerSequence {

    private JugglerSequence() {
        // Utility class; prevent instantiation
    }

    public static void jugglerSequence(int start) {
        int current = start;
        List<String> sequence = new ArrayList<>();
        sequence.add(Integer.toString(current));

        while (current != 1) {
            current = nextTerm(current);
            sequence.add(Integer.toString(current));
        }

        System.out.println(String.join(",", sequence));
    }

    private static int nextTerm(int term) {
        if (isEven(term)) {
            return (int) Math.sqrt(term);
        }
        double sqrt = Math.sqrt(term);
        return (int) (sqrt * sqrt * sqrt);
    }

    private static boolean isEven(int number) {
        return (number & 1) == 0;
    }

    public static void main(String[] args) {
        jugglerSequence(3);
    }
}