package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LetterCombinationsOfPhoneNumber {

    private static final char NO_LETTER_MAPPING = '\0';

    private static final String[] KEYPAD_LETTERS =
            new String[] {
                " ",
                String.valueOf(NO_LETTER_MAPPING),
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

    private static List<String> buildCombinations(
            int[] digits, int digitIndex, StringBuilder partialCombination) {

        if (digitIndex == digits.length) {
            return new ArrayList<>(Collections.singletonList(partialCombination.toString()));
        }

        int digit = digits[digitIndex];
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input digits must be in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();
        String lettersForDigit = KEYPAD_LETTERS[digit];

        for (char letter : lettersForDigit.toCharArray()) {
            boolean isMappedLetter = letter != NO_LETTER_MAPPING;
            if (isMappedLetter) {
                partialCombination.append(letter);
            }

            combinations.addAll(buildCombinations(digits, digitIndex + 1, partialCombination));

            if (isMappedLetter) {
                partialCombination.deleteCharAt(partialCombination.length() - 1);
            }
        }

        return combinations;
    }
}