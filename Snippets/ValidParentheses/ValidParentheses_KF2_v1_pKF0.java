package com.thealgorithms.strings;

public final class ValidParentheses {

    private ValidParentheses() {
        // Utility class; prevent instantiation
    }

    public static boolean isValid(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }

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
                    if (!matches(stack, --head, '{')) {
                        return false;
                    }
                    break;
                case ')':
                    if (!matches(stack, --head, '(')) {
                        return false;
                    }
                    break;
                case ']':
                    if (!matches(stack, --head, '[')) {
                        return false;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected character: " + c);
            }

            if (head < 0) {
                return false;
            }
        }

        return head == 0;
    }

    public static boolean isValidParentheses(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }

        char[] stack = new char[s.length()];
        int top = -1;

        final String openBrackets = "({[";
        final String closeBrackets = ")}]";

        for (char ch : s.toCharArray()) {
            int openIndex = openBrackets.indexOf(ch);
            if (openIndex != -1) {
                stack[++top] = ch;
                continue;
            }

            int closeIndex = closeBrackets.indexOf(ch);
            if (closeIndex == -1 || top < 0) {
                return false;
            }

            if (openBrackets.indexOf(stack[top]) == closeIndex) {
                top--;
            } else {
                return false;
            }
        }

        return top == -1;
    }

    private static boolean matches(char[] stack, int index, char expected) {
        return index >= 0 && stack[index] == expected;
    }
}