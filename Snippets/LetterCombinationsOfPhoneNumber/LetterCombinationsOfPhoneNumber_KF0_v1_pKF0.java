package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LetterCombinationsOfPhoneNumber {

    private static final char EMPTY = '\0';

    /** Mapping of digits to corresponding letters on a phone keypad. */
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
        // Utility class; prevent instantiation
    }

    /**
     * Generates a list of all possible letter combinations that the provided
     * array of numbers could represent on a phone keypad.
     *
     * @param numbers an array of integers representing the phone numbers
     * @return a list of possible letter combinations
     */
    public static List<String> getCombinations(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return List.of("");
        }
        return generateCombinations(numbers, 0, new StringBuilder());
    }

    /**
     * Recursive method to generate combinations of letters from the phone keypad.
     *
     * @param numbers the input array of phone numbers
     * @param index   the current index in the numbers array being processed
     * @param current a StringBuilder holding the current combination of letters
     * @return a list of letter combinations formed from the given numbers
     */
    private static List<String> generateCombinations(int[] numbers, int index, StringBuilder current) {
        if (index == numbers.length) {
            return new ArrayList<>(Collections.singletonList(current.toString()));
        }

        int digit = numbers[index];
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input numbers must be in the range [0, 9]");
        }

        String letters = KEYPAD[digit];
        List<String> combinations = new ArrayList<>();

        for (char letter : letters.toCharArray()) {
            boolean appended = false;
            if (letter != EMPTY) {
                current.append(letter);
                appended = true;
            }

            combinations.addAll(generateCombinations(numbers, index + 1, current));

            if (appended) {
                current.deleteCharAt(current.length() - 1);
            }
        }

        return combinations;
    }
}