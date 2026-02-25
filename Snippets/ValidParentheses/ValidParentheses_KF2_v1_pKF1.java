package com.thealgorithms.strings;

public final class ValidParentheses {

    private ValidParentheses() {
    }

    public static boolean isValid(String input) {
        char[] stack = new char[input.length()];
        int stackSize = 0;

        for (char currentChar : input.toCharArray()) {
            switch (currentChar) {
                case '{':
                case '[':
                case '(':
                    stack[stackSize++] = currentChar;
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
                    throw new IllegalArgumentException("Unexpected character: " + currentChar);
            }
        }
        return stackSize == 0;
    }

    public static boolean isValidParentheses(String input) {
        int topIndex = -1;
        char[] stack = new char[input.length()];
        String openingBrackets = "({[";
        String closingBrackets = ")}]";

        for (char currentChar : input.toCharArray()) {
            if (openingBrackets.indexOf(currentChar) != -1) {
                stack[++topIndex] = currentChar;
            } else {
                if (topIndex >= 0
                        && openingBrackets.indexOf(stack[topIndex]) == closingBrackets.indexOf(currentChar)) {
                    topIndex--;
                } else {
                    return false;
                }
            }
        }
        return topIndex == -1;
    }
}