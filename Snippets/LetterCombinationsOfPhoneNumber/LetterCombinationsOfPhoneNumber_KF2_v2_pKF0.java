package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
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
        List<String> result = new ArrayList<>();
        generateCombinations(numbers, 0, new StringBuilder(), result);
        return result;
    }

    private static void generateCombinations(
        int[] numbers,
        int index,
        StringBuilder current,
        List<String> result
    ) {
        if (index == numbers.length) {
            result.add(current.toString());
            return;
        }

        int digit = numbers[index];
        validateDigit(digit);

        String letters = KEYPAD[digit];
        for (char letter : letters.toCharArray()) {
            boolean isEmptyLetter = (letter == EMPTY);

            if (!isEmptyLetter) {
                current.append(letter);
            }

            generateCombinations(numbers, index + 1, current, result);

            if (!isEmptyLetter) {
                current.deleteCharAt(current.length() - 1);
            }
        }
    }

    private static void validateDigit(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input numbers must be in the range [0, 9]");
        }
    }
}