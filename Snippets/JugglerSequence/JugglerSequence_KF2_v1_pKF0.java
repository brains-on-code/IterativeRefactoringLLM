package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class JugglerSequence {

    private JugglerSequence() {
        // Utility class; prevent instantiation
    }

    public static void jugglerSequence(int inputNumber) {
        int current = inputNumber;
        List<String> sequence = new ArrayList<>();
        sequence.add(String.valueOf(current));

        while (current != 1) {
            current = nextJugglerTerm(current);
            sequence.add(String.valueOf(current));
        }

        String result = String.join(",", sequence);
        System.out.println(result);
    }

    private static int nextJugglerTerm(int n) {
        if (n % 2 == 0) {
            return (int) Math.floor(Math.sqrt(n));
        } else {
            double sqrtN = Math.sqrt(n);
            return (int) Math.floor(sqrtN * sqrtN * sqrtN);
        }
    }

    public static void main(String[] args) {
        jugglerSequence(3);
    }
}