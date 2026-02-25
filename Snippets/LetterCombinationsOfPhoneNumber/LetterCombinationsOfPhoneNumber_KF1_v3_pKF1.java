package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PhoneNumberLetterCombinations {

    private static final char PLACEHOLDER_CHAR = '\0';

    private static final String[] DIGIT_TO_LETTERS_MAP =
            new String[] {" ", String.valueOf(PLACEHOLDER_CHAR), "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    private PhoneNumberLetterCombinations() {
    }

    public static List<String> getLetterCombinations(int[] phoneNumberDigits) {
        if (phoneNumberDigits == null) {
            return List.of("");
        }
        return buildCombinations(phoneNumberDigits, 0, new StringBuilder());
    }

    private static List<String> buildCombinations(int[] phoneNumberDigits, int digitIndex, StringBuilder partialCombination) {
        if (digitIndex == phoneNumberDigits.length) {
            return new ArrayList<>(Collections.singletonList(partialCombination.toString()));
        }

        final int digit = phoneNumberDigits[digitIndex];
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input numbers must be in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();

        for (char letter : DIGIT_TO_LETTERS_MAP[digit].toCharArray()) {
            if (letter != PLACEHOLDER_CHAR) {
                partialCombination.append(letter);
            }
            combinations.addAll(buildCombinations(phoneNumberDigits, digitIndex + 1, partialCombination));
            if (letter != PLACEHOLDER_CHAR) {
                partialCombination.deleteCharAt(partialCombination.length() - 1);
            }
        }

        return combinations;
    }
}