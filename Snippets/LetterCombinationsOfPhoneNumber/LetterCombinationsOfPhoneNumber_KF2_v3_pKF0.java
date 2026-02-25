package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.List;

public final class LetterCombinationsOfPhoneNumber {

    private static final char EMPTY = '\0';

    private static final String[] KEYPAD = {
        " ",
        String.valueOf(EMPTY),
        "abc",
        "def",
        "ghi",
        "jkl",
        "mno",
        "pqrs",
        "tuv",
        "wxyz"
    };

    private LetterCombinationsOfPhoneNumber() {
        // Utility class; prevent instantiation
    }

    public static List<String> getCombinations(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return List.of("");
        }

        List<String> combinations = new ArrayList<>();
        buildCombinations(numbers, 0, new StringBuilder(), combinations);
        return combinations;
    }

    private static void buildCombinations(
        int[] numbers,
        int index,
        StringBuilder currentCombination,
        List<String> combinations
    ) {
        if (index == numbers.length) {
            combinations.add(currentCombination.toString());
            return;
        }

        int digit = numbers[index];
        validateDigit(digit);

        String letters = KEYPAD[digit];
        for (char letter : letters.toCharArray()) {
            if (letter != EMPTY) {
                currentCombination.append(letter);
            }

            buildCombinations(numbers, index + 1, currentCombination, combinations);

            if (letter != EMPTY) {
                currentCombination.deleteCharAt(currentCombination.length() - 1);
            }
        }
    }

    private static void validateDigit(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input numbers must be in the range [0, 9]");
        }
    }
}