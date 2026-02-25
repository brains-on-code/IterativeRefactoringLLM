package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PhoneNumberLetterCombinations {

    private static final char NO_LETTER_PLACEHOLDER = '\0';

    private static final String[] DIGIT_TO_LETTERS_MAP =
            new String[] {
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

    private PhoneNumberLetterCombinations() {}

    public static List<String> getLetterCombinations(int[] digits) {
        if (digits == null) {
            return List.of("");
        }
        return generateCombinations(digits, 0, new StringBuilder());
    }

    private static List<String> generateCombinations(
            int[] digits, int digitIndex, StringBuilder partialCombination) {

        if (digitIndex == digits.length) {
            return new ArrayList<>(Collections.singletonList(partialCombination.toString()));
        }

        final int digit = digits[digitIndex];
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input numbers must be in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();

        for (char letter : DIGIT_TO_LETTERS_MAP[digit].toCharArray()) {
            boolean isPlaceholder = (letter == NO_LETTER_PLACEHOLDER);

            if (!isPlaceholder) {
                partialCombination.append(letter);
            }

            combinations.addAll(
                    generateCombinations(digits, digitIndex + 1, partialCombination));

            if (!isPlaceholder) {
                partialCombination.deleteCharAt(partialCombination.length() - 1);
            }
        }

        return combinations;
    }
}