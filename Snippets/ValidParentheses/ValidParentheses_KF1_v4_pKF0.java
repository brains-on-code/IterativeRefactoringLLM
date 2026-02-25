package com.thealgorithms.strings;

import java.util.Deque;
import java.util.ArrayDeque;

public final class ParenthesesValidator {

    private static final String OPENING_BRACKETS = "({[";
    private static final String CLOSING_BRACKETS = ")}]";

    private ParenthesesValidator() {
        // Utility class; prevent instantiation
    }

    /**
     * Validates that the input string has properly balanced and ordered brackets.
     * Allowed characters: '(', ')', '{', '}', '[' and ']'.
     *
     * @param input the string to validate
     * @return true if the brackets are balanced; false otherwise
     * @throws IllegalArgumentException if an unexpected character is encountered
     */
    public static boolean isBalancedStrict(String input) {
        validateNotNull(input);
        return isBalancedInternal(input, true);
    }

    /**
     * Validates that the input string has properly balanced and ordered brackets.
     * Allowed characters: '(', ')', '{', '}', '[' and ']'.
     *
     * @param input the string to validate
     * @return true if the brackets are balanced; false otherwise
     */
    public static boolean isBalanced(String input) {
        if (input == null) {
            return false;
        }
        return isBalancedInternal(input, false);
    }

    private static boolean isBalancedInternal(String input, boolean strict) {
        Deque<Character> stack = new ArrayDeque<>(input.length());

        for (char ch : input.toCharArray()) {
            if (isOpeningBracket(ch)) {
                stack.push(ch);
            } else if (isClosingBracket(ch)) {
                if (stack.isEmpty() || !isMatchingPair(stack.pop(), ch)) {
                    return false;
                }
            } else if (strict) {
                throw new IllegalArgumentException("Unexpected character: " + ch);
            }
        }

        return stack.isEmpty();
    }

    private static void validateNotNull(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
    }

    private static boolean isOpeningBracket(char ch) {
        return OPENING_BRACKETS.indexOf(ch) >= 0;
    }

    private static boolean isClosingBracket(char ch) {
        return CLOSING_BRACKETS.indexOf(ch) >= 0;
    }

    private static boolean isMatchingPair(char opening, char closing) {
        return OPENING_BRACKETS.indexOf(opening) == CLOSING_BRACKETS.indexOf(closing);
    }
}