package com.thealgorithms.strings;

public final class ValidParentheses {

    private ValidParentheses() {
    }

    public static boolean isValid(String input) {
        char[] stack = new char[input.length()];
        int stackSize = 0;

        for (char ch : input.toCharArray()) {
            switch (ch) {
                case '{':
                case '[':
                case '(':
                    stack[stackSize++] = ch;
                    break;
                case '}':
                    if (stackSize == 0 || stack[--stackSize] != '{') {
                        return false;
                    }
                    break;
                case ')':
                    if (stackSize == 0 || stack[--stackSize] != '(') {
                        return false;
                    }
                    break;
                case ']':
                    if (stackSize == 0 || stack[--stackSize] != '[') {
                        return false;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected character: " + ch);
            }
        }
        return stackSize == 0;
    }

    public static boolean isValidParentheses(String input) {
        int top = -1;
        char[] stack = new char[input.length()];
        String opening = "({[";
        String closing = ")}]";

        for (char ch : input.toCharArray()) {
            if (opening.indexOf(ch) != -1) {
                stack[++top] = ch;
            } else {
                if (top >= 0
                        && opening.indexOf(stack[top]) == closing.indexOf(ch)) {
                    top--;
                } else {
                    return false;
                }
            }
        }
        return top == -1;
    }
}