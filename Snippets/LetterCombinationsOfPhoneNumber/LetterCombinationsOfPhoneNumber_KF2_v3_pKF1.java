package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LetterCombinationsOfPhoneNumber {

    private static final char EMPTY_LETTER = '\0';

    private static final String[] DIGIT_TO_LETTERS = {
        " ",
        String.valueOf(EMPTY_LETTER),
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
        return buildCombinations(digits, 0, new StringBuilder());
    }

    private static List<String> buildCombinations(int[] digits, int index, StringBuilder current) {
        if (index == digits.length) {
            return new ArrayList<>(Collections.singletonList(current.toString()));
        }

        int digit = digits[index];
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input digits must be in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();
        String letters = DIGIT_TO_LETTERS[digit];

        for (char letter : letters.toCharArray()) {
            boolean isEmptyLetter = (letter == EMPTY_LETTER);

            if (!isEmptyLetter) {
                current.append(letter);
            }

            combinations.addAll(buildCombinations(digits, index + 1, current));

            if (!isEmptyLetter) {
                current.deleteCharAt(current.length() - 1);
            }
        }

        return combinations;
    }
}