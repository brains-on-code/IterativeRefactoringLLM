package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LetterCombinationsOfPhoneNumber {

    private static final char EMPTY_CHAR = '\0';

    private static final String[] DIGIT_TO_LETTERS =
            new String[] {" ", String.valueOf(EMPTY_CHAR), "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    private LetterCombinationsOfPhoneNumber() {}

    public static List<String> getCombinations(int[] digits) {
        if (digits == null) {
            return List.of("");
        }
        return generateCombinations(digits, 0, new StringBuilder());
    }

    private static List<String> generateCombinations(int[] digits, int currentIndex, StringBuilder currentCombination) {
        if (currentIndex == digits.length) {
            return new ArrayList<>(Collections.singletonList(currentCombination.toString()));
        }

        int digit = digits[currentIndex];
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input digits must be in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();

        for (char letter : DIGIT_TO_LETTERS[digit].toCharArray()) {
            if (letter != EMPTY_CHAR) {
                currentCombination.append(letter);
            }
            combinations.addAll(generateCombinations(digits, currentIndex + 1, currentCombination));
            if (letter != EMPTY_CHAR) {
                currentCombination.deleteCharAt(currentCombination.length() - 1);
            }
        }

        return combinations;
    }
}