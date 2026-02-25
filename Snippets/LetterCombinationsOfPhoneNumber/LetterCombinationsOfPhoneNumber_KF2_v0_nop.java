package com.thealgorithms.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LetterCombinationsOfPhoneNumber {

    private static final char EMPTY = '\0';

    private static final String[] KEYPAD = new String[] {" ", String.valueOf(EMPTY), "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    private LetterCombinationsOfPhoneNumber() {
    }


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

        final var number = numbers[index];
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("Input numbers must in the range [0, 9]");
        }

        List<String> combinations = new ArrayList<>();

        for (char letter : KEYPAD[number].toCharArray()) {
            if (letter != EMPTY) {
                current.append(letter);
            }
            combinations.addAll(generateCombinations(numbers, index + 1, current));
            if (letter != EMPTY) {
                current.deleteCharAt(current.length() - 1);
            }
        }

        return combinations;
    }
}
