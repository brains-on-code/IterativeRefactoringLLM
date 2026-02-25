package com.thealgorithms.strings;

public final class ValidParentheses {

    private static final String OPEN_BRACKETS = "({[";
    private static final String CLOSE_BRACKETS = ")}]";

    private ValidParentheses() {
        // Utility class; prevent instantiation
    }

    public static boolean isValid(String s) {
        return isValidParentheses(s);
    }

    public static boolean isValidParentheses(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }

        char[] stack = new char[s.length()];
        int top = -1;

        for (char ch : s.toCharArray()) {
            int openIndex = OPEN_BRACKETS.indexOf(ch);
            if (openIndex != -1) {
                stack[++top] = ch;
                continue;
            }

            int closeIndex = CLOSE_BRACKETS.indexOf(ch);
            if (closeIndex == -1 || top < 0) {
                return false;
            }

            if (OPEN_BRACKETS.indexOf(stack[top]) == closeIndex) {
                top--;
            } else {
                return false;
            }
        }

        return top == -1;
    }
}