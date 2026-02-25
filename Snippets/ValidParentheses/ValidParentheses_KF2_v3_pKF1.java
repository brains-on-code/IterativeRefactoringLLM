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
        int stackTop = -1;
        char[] stack = new char[input.length()];
        String openingBrackets = "({[";
        String closingBrackets = ")}]";

        for (char ch : input.toCharArray()) {
            if (openingBrackets.indexOf(ch) != -1) {
                stack[++stackTop] = ch;
            } else {
                if (stackTop >= 0
                        && openingBrackets.indexOf(stack[stackTop])
                                == closingBrackets.indexOf(ch)) {
                    stackTop--;
                } else {
                    return false;
                }
            }
        }
        return stackTop == -1;
    }
}