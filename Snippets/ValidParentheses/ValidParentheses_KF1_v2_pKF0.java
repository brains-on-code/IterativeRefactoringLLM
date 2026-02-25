package com.thealgorithms.strings;

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
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        char[] stack = new char[input.length()];
        int top = 0;

        for (char ch : input.toCharArray()) {
            if (isOpeningBracket(ch)) {
                stack[top++] = ch;
            } else if (isClosingBracket(ch)) {
                if (top == 0 || !isMatchingPair(stack[--top], ch)) {
                    return false;
                }
            } else {
                throw new IllegalArgumentException("Unexpected character: " + ch);
            }
        }

        return top == 0;
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

        char[] stack = new char[input.length()];
        int top = -1;

        for (char ch : input.toCharArray()) {
            if (isOpeningBracket(ch)) {
                stack[++top] = ch;
            } else if (isClosingBracket(ch)) {
                if (top < 0 || !isMatchingPair(stack[top--], ch)) {
                    return false;
                }
            } else {
                // Ignore non-bracket characters in non-strict mode
            }
        }

        return top == -1;
    }

    private static boolean isOpeningBracket(char ch) {
        return OPENING_BRACKETS.indexOf(ch) != -1;
    }

    private static boolean isClosingBracket(char ch) {
        return CLOSING_BRACKETS.indexOf(ch) != -1;
    }

    private static boolean isMatchingPair(char opening, char closing) {
        return OPENING_BRACKETS.indexOf(opening) == CLOSING_BRACKETS.indexOf(closing);
    }
}