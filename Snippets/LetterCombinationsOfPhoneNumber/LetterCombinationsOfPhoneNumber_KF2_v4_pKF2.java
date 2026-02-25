package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LetterCombinationsOfPhoneNumber {

    private static final char EMPTY = '\0';

    /**
     * Digit-to-letters mapping for a phone keypad.
     * Index: 0 -> " " (space), 1 -> EMPTY, 2 -> "abc", ..., 9 -> "wxyz".
     */
    private static final String[] KEYPAD = {
        " ",
        String.valueOf(EMPTY),
        "abc",
        "def",
        "ghi",
        "jkl",
        "mno",
        "pqrs",
        "tuv",
        "wxyz"
    };

    private LetterCombinationsOfPhoneNumber() {
        // Prevent instantiation
    }

    /**
     * Returns all possible letter combinations for the given sequence of digits.
     *
     * @param numbers array of digits in the range [0, 9]
     * @return list of all possible letter combinations; if {@code numbers} is null,
     *         returns a list containing an empty string
     * @throws IllegalArgumentException if any digit is outside the range [0, 9]
     */
    public static List<String> getCombinations(int[] numbers) {
        if (numbers == null) {
            return List.of("");
        }
        return generateCombinations(numbers, 0, new StringBuilder());
    }

    /**
     * Recursively builds all combinations by traversing the digit array.
     *
     * @param numbers array of digits
     * @param index   current position in the array
     * @param current current partial combination
     * @return list of combinations formed from {@code index} onward
     */
    private static List<String> generateCombinations(int[] numbers, int index, StringBuilder current) {
        if (index == numbers.length) {
            return new ArrayList<>(Collections.singletonList(current.toString()));
        }

        int digit = numbers[index];
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input numbers must be in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();
        String letters = KEYPAD[digit];

        for (char letter : letters.toCharArray()) {
            boolean isNonEmptyLetter = (letter != EMPTY);

            if (isNonEmptyLetter) {
                current.append(letter);
            }

            combinations.addAll(generateCombinations(numbers, index + 1, current));

            if (isNonEmptyLetter) {
                current.deleteCharAt(current.length() - 1);
            }
        }

        return combinations;
    }
}