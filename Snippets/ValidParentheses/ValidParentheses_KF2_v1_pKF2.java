package com.thealgorithms.strings;

public final class ValidParentheses {

    private ValidParentheses() {
        // Utility class; prevent instantiation
    }

    /**
     * Validates a string of parentheses using a char array as a stack.
     * Supports: (), {}, [].
     *
     * @param s the input string containing only parentheses characters
     * @return true if the parentheses are balanced and properly nested; false otherwise
     * @throws IllegalArgumentException if an unexpected character is encountered
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
     * Validates a string of parentheses using index-based matching.
     * Supports: (), {}, [].
     *
     * @param s the input string containing only parentheses characters
     * @return true if the parentheses are balanced and properly nested; false otherwise
     */
    public static boolean isValidParentheses(String s) {
        int top = -1;
        char[] stack = new char[s.length()];
        String openBrackets = "({[";
        String closeBrackets = ")}]";

        for (char ch : s.toCharArray()) {
            int openIndex = openBrackets.indexOf(ch);

            // If it's an opening bracket, push onto the stack
            if (openIndex != -1) {
                stack[++top] = ch;
                continue;
            }

            // If it's a closing bracket, stack must not be empty
            if (top < 0) {
                return false;
            }

            char topChar = stack[top];
            int topOpenIndex = openBrackets.indexOf(topChar);
            int closeIndex = closeBrackets.indexOf(ch);

            // Check if the type of the top opening bracket matches this closing bracket
            if (topOpenIndex == closeIndex) {
                top--; // Pop the stack
            } else {
                return false;
            }
        }

        // All brackets must be closed
        return top == -1;
    }
}