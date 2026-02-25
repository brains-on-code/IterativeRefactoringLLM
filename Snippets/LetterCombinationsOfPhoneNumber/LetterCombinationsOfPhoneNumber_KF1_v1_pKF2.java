package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class to generate all possible letter combinations for a sequence of
 * digits based on the classic phone keypad mapping.
 */
public final class Class1 {

    /** Placeholder character used for digits that do not map to letters (e.g., 0, 1). */
    private static final char PLACEHOLDER = '\0';

    /**
     * Phone keypad mapping:
     * index 0 -> " " (space)
     * index 1 -> PLACEHOLDER (no letters)
     * index 2 -> "abc"
     * index 3 -> "def"
     * index 4 -> "ghi"
     * index 5 -> "jkl"
     * index 6 -> "mno"
     * index 7 -> "pqrs"
     * index 8 -> "tuv"
     * index 9 -> "wxyz"
     */
    private static final String[] DIGIT_TO_LETTERS =
            new String[] {" ", String.valueOf(PLACEHOLDER), "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Generates all possible letter combinations for the given array of digits
     * using the phone keypad mapping.
     *
     * @param digits an array of digits in the range [0, 9]
     * @return a list of all possible letter combinations; if {@code digits} is
     *         {@code null}, returns a list containing an empty string
     * @throws IllegalArgumentException if any digit is outside the range [0, 9]
     */
    public static List<String> method1(int[] digits) {
        if (digits == null) {
            return List.of("");
        }
        return generateCombinations(digits, 0, new StringBuilder());
    }

    /**
     * Recursive helper that builds combinations by traversing the digit array.
     *
     * @param digits the input digit array
     * @param index  current position in the digit array
     * @param current current partial combination
     * @return list of combinations generated from the current state
     */
    private static List<String> generateCombinations(int[] digits, int index, StringBuilder current) {
        if (index == digits.length) {
            return new ArrayList<>(Collections.singletonList(current.toString()));
        }

        int digit = digits[index];
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input numbers must be in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();

        for (char letter : DIGIT_TO_LETTERS[digit].toCharArray()) {
            boolean isPlaceholder = (letter == PLACEHOLDER);

            if (!isPlaceholder) {
                current.append(letter);
            }

            combinations.addAll(generateCombinations(digits, index + 1, current));

            if (!isPlaceholder) {
                current.deleteCharAt(current.length() - 1);
            }
        }

        return combinations;
    }
}