package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LetterCombinationsOfPhoneNumber {

    private static final char EMPTY_CHAR = '\0';

    // Mapping of digits to corresponding letters on a phone keypad
    private static final String[] DIGIT_TO_LETTERS =
            new String[] {" ", String.valueOf(EMPTY_CHAR), "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    private LetterCombinationsOfPhoneNumber() {
    }

    /**
     * Generates a list of all possible letter combinations that the provided
     * array of digits could represent on a phone keypad.
     *
     * @param digits an array of integers representing the phone digits
     * @return a list of possible letter combinations
     */
    public static List<String> getCombinations(int[] digits) {
        if (digits == null) {
            return List.of("");
        }
        return generateCombinations(digits, 0, new StringBuilder());
    }

    /**
     * Recursive method to generate combinations of letters from the phone keypad.
     *
     * @param digits          the input array of phone digits
     * @param currentIndex    the current index in the digits array being processed
     * @param currentCombination a StringBuilder holding the current combination of letters
     * @return a list of letter combinations formed from the given digits
     */
    private static List<String> generateCombinations(int[] digits, int currentIndex, StringBuilder currentCombination) {
        if (currentIndex == digits.length) {
            return new ArrayList<>(Collections.singletonList(currentCombination.toString()));
        }

        int currentDigit = digits[currentIndex];
        if (currentDigit < 0 || currentDigit > 9) {
            throw new IllegalArgumentException("Input digits must be in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();
        String lettersForDigit = DIGIT_TO_LETTERS[currentDigit];

        for (char letter : lettersForDigit.toCharArray()) {
            boolean isNonEmptyLetter = letter != EMPTY_CHAR;
            if (isNonEmptyLetter) {
                currentCombination.append(letter);
            }

            combinations.addAll(generateCombinations(digits, currentIndex + 1, currentCombination));

            if (isNonEmptyLetter) {
                currentCombination.deleteCharAt(currentCombination.length() - 1);
            }
        }

        return combinations;
    }
}