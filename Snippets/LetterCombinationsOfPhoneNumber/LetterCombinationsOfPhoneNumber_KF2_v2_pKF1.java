package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LetterCombinationsOfPhoneNumber {

    private static final char NO_LETTER_PLACEHOLDER = '\0';

    private static final String[] DIGIT_TO_LETTERS_MAP = {
        " ",
        String.valueOf(NO_LETTER_PLACEHOLDER),
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

    private static List<String> generateCombinations(int[] digits, int digitIndex, StringBuilder currentCombination) {
        if (digitIndex == digits.length) {
            return new ArrayList<>(Collections.singletonList(currentCombination.toString()));
        }

        int currentDigit = digits[digitIndex];
        if (currentDigit < 0 || currentDigit > 9) {
            throw new IllegalArgumentException("Input digits must be in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();
        String lettersForDigit = DIGIT_TO_LETTERS_MAP[currentDigit];

        for (char letter : lettersForDigit.toCharArray()) {
            boolean isPlaceholder = (letter == NO_LETTER_PLACEHOLDER);

            if (!isPlaceholder) {
                currentCombination.append(letter);
            }

            combinations.addAll(generateCombinations(digits, digitIndex + 1, currentCombination));

            if (!isPlaceholder) {
                currentCombination.deleteCharAt(currentCombination.length() - 1);
            }
        }

        return combinations;
    }
}