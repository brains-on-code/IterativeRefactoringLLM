package com.thealgorithms.strings;

import java.util.ArrayList;
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

        List<String> combinations = new ArrayList<>();
        buildCombinations(numbers, 0, new StringBuilder(), combinations);
        return combinations;
    }

    /**
     * Recursive method to generate combinations of letters from the phone keypad.
     *
     * @param numbers the input array of phone numbers
     * @param index   the current index in the numbers array being processed
     * @param current a StringBuilder holding the current combination of letters
     * @param result  the list collecting all letter combinations
     */
    private static void buildCombinations(
        int[] numbers,
        int index,
        StringBuilder current,
        List<String> result
    ) {
        if (index == numbers.length) {
            result.add(current.toString());
            return;
        }

        int digit = numbers[index];
        validateDigit(digit);

        String letters = KEYPAD[digit];
        for (int i = 0; i < letters.length(); i++) {
            char letter = letters.charAt(i);
            int originalLength = current.length();

            if (letter != EMPTY) {
                current.append(letter);
            }

            buildCombinations(numbers, index + 1, current, result);
            current.setLength(originalLength);
        }
    }

    private static void validateDigit(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input numbers must be in the range [0, 9]");
        }
    }
}