package com.thealgorithms.strings;

/**
 * Utility class for validating strings containing only bracket characters.
 *
 * <p>A string is considered valid if:
 * <ul>
 *   <li>Open brackets are closed by the same type of brackets.</li>
 *   <li>Open brackets are closed in the correct order.</li>
 *   <li>Every closing bracket has a corresponding open bracket of the same type.</li>
 * </ul>
 */
public final class ValidParentheses {

    private static final String OPEN_BRACKETS = "({[";
    private static final String CLOSE_BRACKETS = ")}]";

    private ValidParentheses() {
        // Utility class; prevent instantiation.
    }

    /**
     * Validates a string of brackets using a char-array stack and switch logic.
     *
     * @param input the string containing only bracket characters
     * @return {@code true} if the string is valid; {@code false} otherwise
     * @throws IllegalArgumentException if the string contains non-bracket characters
     */
    public static boolean isValid(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }

        char[] stack = new char[input.length()];
        int top = 0;

        for (char ch : input.toCharArray()) {
            if (isOpenBracket(ch)) {
                stack[top++] = ch;
                continue;
            }

            if (isCloseBracket(ch)) {
                if (top == 0) {
                    return false;
                }
                char expectedOpen = getMatchingOpenBracket(ch);
                if (!isMatchingBracket(stack, top - 1, expectedOpen)) {
                    return false;
                }
                top--;
                continue;
            }

            throw new IllegalArgumentException("Unexpected character: " + ch);
        }

        return top == 0;
    }

    /**
     * Validates a string of brackets using index-based matching of open/close sets.
     *
     * @param input the string containing only bracket characters
     * @return {@code true} if the string is valid; {@code false} otherwise
     */
    public static boolean isValidParentheses(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }

        char[] stack = new char[input.length()];
        int top = -1;

        for (char ch : input.toCharArray()) {
            int openIndex = OPEN_BRACKETS.indexOf(ch);

            if (openIndex != -1) {
                stack[++top] = ch;
                continue;
            }

            int closeIndex = CLOSE_BRACKETS.indexOf(ch);
            if (closeIndex == -1) {
                return false;
            }

            if (top < 0) {
                return false;
            }

            char expectedOpen = OPEN_BRACKETS.charAt(closeIndex);
            if (!isMatchingBracket(stack, top, expectedOpen)) {
                return false;
            }

            top--;
        }

        return top == -1;
    }

    private static boolean isMatchingBracket(char[] stack, int index, char expected) {
        return index >= 0 && stack[index] == expected;
    }

    private static boolean isOpenBracket(char ch) {
        return OPEN_BRACKETS.indexOf(ch) != -1;
    }

    private static boolean isCloseBracket(char ch) {
        return CLOSE_BRACKETS.indexOf(ch) != -1;
    }

    private static char getMatchingOpenBracket(char closeBracket) {
        int index = CLOSE_BRACKETS.indexOf(closeBracket);
        if (index == -1) {
            throw new IllegalArgumentException("Unexpected closing bracket: " + closeBracket);
        }
        return OPEN_BRACKETS.charAt(index);
    }
}