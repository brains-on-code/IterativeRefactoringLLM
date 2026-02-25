package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PhoneNumberLetterCombinations {

    private static final char EMPTY_DIGIT_PLACEHOLDER = '\0';

    private static final String[] DIGIT_TO_LETTERS =
            new String[] {
                " ",
                String.valueOf(EMPTY_DIGIT_PLACEHOLDER),
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

    public static List<String> getLetterCombinations(int[] phoneNumberDigits) {
        if (phoneNumberDigits == null) {
            return List.of("");
        }
        return buildCombinations(phoneNumberDigits, 0, new StringBuilder());
    }

    private static List<String> buildCombinations(
            int[] phoneNumberDigits, int currentDigitIndex, StringBuilder currentCombination) {

        if (currentDigitIndex == phoneNumberDigits.length) {
            return new ArrayList<>(Collections.singletonList(currentCombination.toString()));
        }

        final int currentDigit = phoneNumberDigits[currentDigitIndex];
        if (currentDigit < 0 || currentDigit > 9) {
            throw new IllegalArgumentException("Input numbers must be in the range [0, 9]");
        }

        List<String> allCombinations = new ArrayList<>();

        for (char mappedLetter : DIGIT_TO_LETTERS[currentDigit].toCharArray()) {
            if (mappedLetter != EMPTY_DIGIT_PLACEHOLDER) {
                currentCombination.append(mappedLetter);
            }

            allCombinations.addAll(
                    buildCombinations(phoneNumberDigits, currentDigitIndex + 1, currentCombination));

            if (mappedLetter != EMPTY_DIGIT_PLACEHOLDER) {
                currentCombination.deleteCharAt(currentCombination.length() - 1);
            }
        }

        return allCombinations;
    }
}