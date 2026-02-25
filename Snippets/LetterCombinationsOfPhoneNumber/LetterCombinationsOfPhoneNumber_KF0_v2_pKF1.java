package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LetterCombinationsOfPhoneNumber {

    private static final char PLACEHOLDER_CHAR = '\0';

    // Mapping of digits to corresponding letters on a phone keypad
    private static final String[] KEYPAD_MAPPING =
            new String[] {" ", String.valueOf(PLACEHOLDER_CHAR), "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

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
     * @param digits              the input array of phone digits
     * @param digitIndex          the current index in the digits array being processed
     * @param currentCombination  a StringBuilder holding the current combination of letters
     * @return a list of letter combinations formed from the given digits
     */
    private static List<String> generateCombinations(int[] digits, int digitIndex, StringBuilder currentCombination) {
        if (digitIndex == digits.length) {
            return new ArrayList<>(Collections.singletonList(currentCombination.toString()));
        }

        int digit = digits[digitIndex];
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input digits must be in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();
        String lettersForDigit = KEYPAD_MAPPING[digit];

        for (char letter : lettersForDigit.toCharArray()) {
            boolean shouldAppendLetter = letter != PLACEHOLDER_CHAR;
            if (shouldAppendLetter) {
                currentCombination.append(letter);
            }

            combinations.addAll(generateCombinations(digits, digitIndex + 1, currentCombination));

            if (shouldAppendLetter) {
                currentCombination.deleteCharAt(currentCombination.length() - 1);
            }
        }

        return combinations;
    }
}