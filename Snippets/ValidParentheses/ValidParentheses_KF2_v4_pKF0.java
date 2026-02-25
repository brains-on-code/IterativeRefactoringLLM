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
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }

        Deque<Character> stack = new ArrayDeque<>();

        for (char ch : input.toCharArray()) {
            if (isOpeningBracket(ch)) {
                stack.push(ch);
            } else if (isClosingBracket(ch)) {
                if (!matchesTopOfStack(stack, ch)) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    private static boolean isOpeningBracket(char ch) {
        return BRACKET_PAIRS.containsValue(ch);
    }

    private static boolean isClosingBracket(char ch) {
        return BRACKET_PAIRS.containsKey(ch);
    }

    private static boolean matchesTopOfStack(Deque<Character> stack, char closingBracket) {
        return !stack.isEmpty() && stack.pop().equals(BRACKET_PAIRS.get(closingBracket));
    }
}