package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Class1 {

    private static final char EMPTY_CHAR = '\0';

    private static final String[] DIGIT_TO_LETTERS =
            new String[] {" ", String.valueOf(EMPTY_CHAR), "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    private Class1() {
    }

    public static List<String> method1(int[] digits) {
        if (digits == null) {
            return List.of("");
        }
        return generateCombinations(digits, 0, new StringBuilder());
    }

    private static List<String> generateCombinations(int[] digits, int index, StringBuilder currentCombination) {
        if (index == digits.length) {
            return new ArrayList<>(Collections.singletonList(currentCombination.toString()));
        }

        final int digit = digits[index];
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input numbers must in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();

        for (char letter : DIGIT_TO_LETTERS[digit].toCharArray()) {
            if (letter != EMPTY_CHAR) {
                currentCombination.append(letter);
            }
            combinations.addAll(generateCombinations(digits, index + 1, currentCombination));
            if (letter != EMPTY_CHAR) {
                currentCombination.deleteCharAt(currentCombination.length() - 1);
            }
        }

        return combinations;
    }
}