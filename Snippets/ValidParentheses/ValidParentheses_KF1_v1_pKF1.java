package com.thealgorithms.strings;

public final class ParenthesesChecker {

    private ParenthesesChecker() {
    }

    public static boolean isBalancedUsingArray(String input) {
        char[] stack = new char[input.length()];
        int stackPointer = 0;

        for (char ch : input.toCharArray()) {
            switch (ch) {
                case '{':
                case '[':
                case '(':
                    stack[stackPointer++] = ch;
                    break;
                case '}':
                    if (stackPointer == 0 || stack[--stackPointer] != '{') {
                        return false;
                    }
                    break;
                case ')':
                    if (stackPointer == 0 || stack[--stackPointer] != '(') {
                        return false;
                    }
                    break;
                case ']':
                    if (stackPointer == 0 || stack[--stackPointer] != '[') {
                        return false;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected character: " + ch);
            }
        }
        return stackPointer == 0;
    }

    public static boolean isBalancedUsingIndexMatch(String input) {
        int top = -1;
        char[] stack = new char[input.length()];
        String openingBrackets = "({[";
        String closingBrackets = ")}]";

        for (char ch : input.toCharArray()) {
            if (openingBrackets.indexOf(ch) != -1) {
                stack[++top] = ch;
            } else {
                if (top >= 0 && openingBrackets.indexOf(stack[top]) == closingBrackets.indexOf(ch)) {
                    top--;
                } else {
                    return false;
                }
            }
        }
        return top == -1;
    }
}