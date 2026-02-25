package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LetterCombinationsOfPhoneNumber {

    private static final char EMPTY = '\0';

    private static final String[] KEYPAD = {
        " ",                 // 0
        String.valueOf(EMPTY), // 1 (no letters)
        "abc",               // 2
        "def",               // 3
        "ghi",               // 4
        "jkl",               // 5
        "mno",               // 6
        "pqrs",              // 7
        "tuv",               // 8
        "wxyz"               // 9
    };

    private LetterCombinationsOfPhoneNumber() {}

    public static List<String> getCombinations(int[] numbers) {
        if (numbers == null) {
            return List.of("");
        }
        return generateCombinations(numbers, 0, new StringBuilder());
    }

    private static List<String> generateCombinations(int[] numbers, int index, StringBuilder current) {
        if (index == numbers.length) {
            return new ArrayList<>(Collections.singletonList(current.toString()));
        }

        int digit = numbers[index];
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input numbers must be in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();
        String letters = KEYPAD[digit];

        for (char letter : letters.toCharArray()) {
            boolean shouldAppend = letter != EMPTY;

            if (shouldAppend) {
                current.append(letter);
            }

            combinations.addAll(generateCombinations(numbers, index + 1, current));

            if (shouldAppend) {
                current.deleteCharAt(current.length() - 1);
            }
        }

        return combinations;
    }
}