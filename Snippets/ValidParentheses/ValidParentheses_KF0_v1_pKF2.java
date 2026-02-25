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

    private ValidParentheses() {
        // Prevent instantiation
    }

    /**
     * Validates a string of brackets using a manual char-array stack and switch logic.
     *
     * @param s the input string containing only '(', ')', '{', '}', '[' and ']'
     * @return {@code true} if the string is valid, {@code false} otherwise
     * @throws IllegalArgumentException if the string contains unexpected characters
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
     * Validates a string of brackets using index-based matching between
     * open and close bracket sets.
     *
     * @param s the input string containing only '(', ')', '{', '}', '[' and ']'
     * @return {@code true} if the string is valid, {@code false} otherwise
     */
    public static boolean isValidParentheses(String s) {
        char[] stack = new char[s.length()];
        int top = -1;

        final String openBrackets = "({[";
        final String closeBrackets = ")}]";

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

            int closeIndex = closeBrackets.indexOf(ch);

            // Indices of matching open/close brackets must be equal
            if (openBrackets.indexOf(stack[top]) == closeIndex) {
                top--;
            } else {
                return false;
            }
        }

        // All brackets must be matched
        return top == -1;
    }
}