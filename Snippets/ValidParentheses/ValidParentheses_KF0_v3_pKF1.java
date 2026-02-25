package com.thealgorithms.strings;

// Given a string containing just the characters '(', ')', '{', '}', '[' and ']',
// determine if the input string is valid. An input string is valid if:
// - Open brackets must be closed by the same type of brackets.
// - Open brackets must be closed in the correct order.
// - Every close bracket has a corresponding open bracket of the same type.
public final class ValidParentheses {

    private ValidParentheses() {}

    public static boolean isValid(String input) {
        char[] stack = new char[input.length()];
        int size = 0;

        for (char ch : input.toCharArray()) {
            switch (ch) {
                case '{':
                case '[':
                case '(':
                    stack[size++] = ch;
                    break;
                case '}':
                    if (size == 0 || stack[--size] != '{') {
                        return false;
                    }
                    break;
                case ')':
                    if (size == 0 || stack[--size] != '(') {
                        return false;
                    }
                    break;
                case ']':
                    if (size == 0 || stack[--size] != '[') {
                        return false;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected character: " + ch);
            }
        }
        return size == 0;
    }

    public static boolean isValidParentheses(String input) {
        char[] stack = new char[input.length()];
        int top = -1;

        String opening = "({[";
        String closing = ")}]";

        for (char ch : input.toCharArray()) {
            if (opening.indexOf(ch) != -1) {
                stack[++top] = ch;
            } else {
                if (top >= 0
                        && opening.indexOf(stack[top])
                                == closing.indexOf(ch)) {
                    top--;
                } else {
                    return false;
                }
            }
        }
        return top == -1;
    }
}