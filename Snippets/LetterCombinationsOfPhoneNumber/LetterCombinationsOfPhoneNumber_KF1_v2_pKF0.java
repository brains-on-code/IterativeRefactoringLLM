package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Class1 {

    private static final char EMPTY_CHAR = '\0';

    private static final String[] DIGIT_TO_LETTERS = {
        " ",
        String.valueOf(EMPTY_CHAR),
        "abc",
        "def",
        "ghi",
        "jkl",
        "mno",
        "pqrs",
        "tuv",
        "wxyz"
    };

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static List<String> method1(int[] digits) {
        if (digits == null) {
            return List.of("");
        }
        return generateCombinations(digits, 0, new StringBuilder());
    }

    private static List<String> generateCombinations(int[] digits, int index, StringBuilder current) {
        if (index == digits.length) {
            return new ArrayList<>(Collections.singletonList(current.toString()));
        }

        int digit = digits[index];
        validateDigit(digit);

        List<String> combinations = new ArrayList<>();
        String letters = DIGIT_TO_LETTERS[digit];

        for (char letter : letters.toCharArray()) {
            boolean appended = appendIfNotEmpty(current, letter);

            combinations.addAll(generateCombinations(digits, index + 1, current));

            if (appended) {
                current.deleteCharAt(current.length() - 1);
            }
        }

        return combinations;
    }

    private static void validateDigit(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input numbers must be in the range [0, 9]");
        }
    }

    private static boolean appendIfNotEmpty(StringBuilder current, char letter) {
        if (letter == EMPTY_CHAR) {
            return false;
        }
        current.append(letter);
        return true;
    }
}