package com.thealgorithms.strings;

public final class ValidParentheses {

    private static final String OPEN_BRACKETS = "({[";
    private static final String CLOSE_BRACKETS = ")}]";

    private ValidParentheses() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether the given string contains only properly nested and matched
     * brackets. Supported bracket types: (), {}, [].
     *
     * @param s a string consisting only of bracket characters
     * @return {@code true} if the string is valid; {@code false} otherwise
     * @throws IllegalArgumentException if a non-bracket character is encountered
     */
    public static boolean isValid(String s) {
        char[] stack = new char[s.length()];
        int head = 0;

        for (char c : s.toCharArray()) {
            switch (c) {
                case '{':
                case '[':
                case '(':
                    stack[head++] = c;
                    break;
                case '}':
                    if (head == 0 || stack[--head] != '{') {
                        return false;
                    }
                    break;
                case ')':
                    if (head == 0 || stack[--head] != '(') {
                        return false;
                    }
                    break;
                case ']':
                    if (head == 0 || stack[--head] != '[') {
                        return false;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected character: " + c);
            }
        }

        return head == 0;
    }

    /**
     * Checks whether the given string contains only properly nested and matched
     * brackets. Matching is performed by comparing the indices of opening and
     * closing brackets in {@link #OPEN_BRACKETS} and {@link #CLOSE_BRACKETS}.
     * Supported bracket types: (), {}, [].
     *
     * @param s a string consisting only of bracket characters
     * @return {@code true} if the string is valid; {@code false} otherwise
     */
    public static boolean isValidParentheses(String s) {
        char[] stack = new char[s.length()];
        int top = -1;

        for (char ch : s.toCharArray()) {
            int openIndex = OPEN_BRACKETS.indexOf(ch);

            // Opening bracket: push onto stack
            if (openIndex != -1) {
                stack[++top] = ch;
                continue;
            }

            // Closing bracket: stack must not be empty
            if (top < 0) {
                return false;
            }

            char topChar = stack[top];
            int topOpenIndex = OPEN_BRACKETS.indexOf(topChar);
            int closeIndex = CLOSE_BRACKETS.indexOf(ch);

            // Valid pair if indices match
            if (topOpenIndex == closeIndex) {
                top--;
            } else {
                return false;
            }
        }

        // All brackets must be closed
        return top == -1;
    }
}