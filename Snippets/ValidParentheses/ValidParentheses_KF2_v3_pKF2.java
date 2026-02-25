package com.thealgorithms.strings;

public final class ValidParentheses {

    private static final String OPEN_BRACKETS = "({[";
    private static final String CLOSE_BRACKETS = ")}]";

    private ValidParentheses() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether the given string of brackets is valid.
     * A string is valid if:
     * - Every opening bracket has a corresponding closing bracket of the same type.
     * - Brackets are closed in the correct order.
     * Supported brackets: (), {}, [].
     *
     * @param s string consisting only of bracket characters
     * @return true if the string is valid; false otherwise
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
     * Checks whether the given string of brackets is valid using index-based matching.
     * A string is valid if:
     * - Every opening bracket has a corresponding closing bracket of the same type.
     * - Brackets are closed in the correct order.
     * Supported brackets: (), {}, [].
     *
     * @param s string consisting only of bracket characters
     * @return true if the string is valid; false otherwise
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

            // Valid pair if indices match; otherwise invalid
            if (topOpenIndex == closeIndex) {
                top--;
            } else {
                return false;
            }
        }

        // All brackets must be matched
        return top == -1;
    }
}