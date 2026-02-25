package com.thealgorithms.strings;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public final class ValidParentheses {

    private static final Map<Character, Character> BRACKET_PAIRS = Map.of(
        ')', '(',
        '}', '{',
        ']', '['
    );

    private ValidParentheses() {
        // Utility class; prevent instantiation
    }

    public static boolean isValid(String input) {
        return isValidParentheses(input);
    }

    public static boolean isValidParentheses(String input) {
        validateInput(input);

        Deque<Character> stack = new ArrayDeque<>();

        for (char ch : input.toCharArray()) {
            if (isOpeningBracket(ch)) {
                stack.push(ch);
            } else if (isClosingBracket(ch)) {
                if (!isMatchingBracket(stack, ch)) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    private static void validateInput(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
    }

    private static boolean isOpeningBracket(char ch) {
        return BRACKET_PAIRS.containsValue(ch);
    }

    private static boolean isClosingBracket(char ch) {
        return BRACKET_PAIRS.containsKey(ch);
    }

    private static boolean isMatchingBracket(Deque<Character> stack, char closingBracket) {
        if (stack.isEmpty()) {
            return false;
        }
        char expectedOpening = BRACKET_PAIRS.get(closingBracket);
        char actualOpening = stack.pop();
        return actualOpening == expectedOpening;
    }
}