package com.thealgorithms.strings;

public final class ParenthesesValidator {

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
        char[] stack = new char[input.length()];
        int top = 0;

        for (char ch : input.toCharArray()) {
            switch (ch) {
                case '{':
                case '[':
                case '(':
                    stack[top++] = ch;
                    break;
                case '}':
                    if (top == 0 || stack[--top] != '{') {
                        return false;
                    }
                    break;
                case ')':
                    if (top == 0 || stack[--top] != '(') {
                        return false;
                    }
                    break;
                case ']':
                    if (top == 0 || stack[--top] != '[') {
                        return false;
                    }
                    break;
                default:
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
        int top = -1;
        char[] stack = new char[input.length()];
        String opening = "({[";
        String closing = ")}]";

        for (char ch : input.toCharArray()) {
            int openingIndex = opening.indexOf(ch);

            if (openingIndex != -1) {
                stack[++top] = ch;
            } else {
                if (top >= 0 && opening.indexOf(stack[top]) == closing.indexOf(ch)) {
                    top--;
                } else {
                    return false;
                }
            }
        }

        return top == -1;
    }
}