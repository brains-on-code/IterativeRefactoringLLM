package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LetterCombinationsOfPhoneNumber {

    private static final char PLACEHOLDER_CHAR = '\0';

    private static final String[] DIGIT_TO_LETTERS_MAP = {
        " ",
        String.valueOf(PLACEHOLDER_CHAR),
        "abc",
        "def",
        "ghi",
        "jkl",
        "mno",
        "pqrs",
        "tuv",
        "wxyz"
    };

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

        int currentDigit = digits[currentIndex];
        if (currentDigit < 0 || currentDigit > 9) {
            throw new IllegalArgumentException("Input digits must be in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();
        String mappedLetters = DIGIT_TO_LETTERS_MAP[currentDigit];

        for (char mappedLetter : mappedLetters.toCharArray()) {
            boolean isPlaceholder = (mappedLetter == PLACEHOLDER_CHAR);

            if (!isPlaceholder) {
                currentCombination.append(mappedLetter);
            }

            combinations.addAll(generateCombinations(digits, currentIndex + 1, currentCombination));

            if (!isPlaceholder) {
                currentCombination.deleteCharAt(currentCombination.length() - 1);
            }
        }

        return combinations;
    }
}