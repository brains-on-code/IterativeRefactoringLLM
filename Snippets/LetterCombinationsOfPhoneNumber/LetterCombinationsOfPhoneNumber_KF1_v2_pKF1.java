package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PhoneNumberLetterCombinations {

    private static final char EMPTY_CHAR = '\0';

    private static final String[] DIGIT_TO_LETTERS =
            new String[] {" ", String.valueOf(EMPTY_CHAR), "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    private PhoneNumberLetterCombinations() {
    }

    public static List<String> getLetterCombinations(int[] digits) {
        if (digits == null) {
            return List.of("");
        }
        return generateCombinations(digits, 0, new StringBuilder());
    }

    private static List<String> generateCombinations(int[] digits, int currentIndex, StringBuilder currentCombination) {
        if (currentIndex == digits.length) {
            return new ArrayList<>(Collections.singletonList(currentCombination.toString()));
        }

        final int currentDigit = digits[currentIndex];
        if (currentDigit < 0 || currentDigit > 9) {
            throw new IllegalArgumentException("Input numbers must be in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();

        for (char letter : DIGIT_TO_LETTERS[currentDigit].toCharArray()) {
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