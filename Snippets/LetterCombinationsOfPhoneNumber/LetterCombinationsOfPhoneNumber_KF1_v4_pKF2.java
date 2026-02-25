package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generates all possible letter combinations for a sequence of digits
 * based on the classic phone keypad mapping.
 */
public final class PhoneKeypadCombinations {

    /**
     * Placeholder character used for digits that do not map to any letters (e.g., 1).
     */
    private static final char PLACEHOLDER = '\0';

    /**
     * Phone keypad mapping:
     * <pre>
     * 0 -> " "      (space)
     * 1 -> PLACEHOLDER (no letters)
     * 2 -> "abc"
     * 3 -> "def"
     * 4 -> "ghi"
     * 5 -> "jkl"
     * 6 -> "mno"
     * 7 -> "pqrs"
     * 8 -> "tuv"
     * 9 -> "wxyz"
     * </pre>
     */
    private static final String[] DIGIT_TO_LETTERS = {
        " ",
        String.valueOf(PLACEHOLDER),
        "abc",
        "def",
        "ghi",
        "jkl",
        "mno",
        "pqrs",
        "tuv",
        "wxyz"
    };

    private PhoneKeypadCombinations() {
        // Utility class; prevent instantiation.
    }

    /**
     * Returns all possible letter combinations for the given array of digits.
     *
     * @param digits array of digits in the range [0, 9]
     * @return list of all possible letter combinations; if {@code digits} is
     *         {@code null}, returns a list containing an empty string
     * @throws IllegalArgumentException if any digit is outside the range [0, 9]
     */
    public static List<String> getCombinations(int[] digits) {
        if (digits == null) {
            return List.of("");
        }
        return generateCombinations(digits, 0, new StringBuilder());
    }

    /**
     * Recursively builds all combinations by traversing the digit array.
     *
     * @param digits  the input digit array
     * @param index   current position in the digit array
     * @param current current partial combination being built
     * @return list of combinations from this recursion branch
     */
    private static List<String> generateCombinations(int[] digits, int index, StringBuilder current) {
        if (index == digits.length) {
            return new ArrayList<>(Collections.singletonList(current.toString()));
        }

        int digit = digits[index];
        validateDigit(digit);

        List<String> combinations = new ArrayList<>();
        String letters = DIGIT_TO_LETTERS[digit];

        for (char letter : letters.toCharArray()) {
            boolean isPlaceholder = (letter == PLACEHOLDER);

            if (!isPlaceholder) {
                current.append(letter);
            }

            combinations.addAll(generateCombinations(digits, index + 1, current));

            if (!isPlaceholder) {
                current.deleteCharAt(current.length() - 1);
            }
        }

        return combinations;
    }

    /**
     * Validates that the digit is within the supported range [0, 9].
     *
     * @param digit the digit to validate
     * @throws IllegalArgumentException if the digit is outside the range [0, 9]
     */
    private static void validateDigit(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Input numbers must be in the range [0, 9]");
        }
    }
}