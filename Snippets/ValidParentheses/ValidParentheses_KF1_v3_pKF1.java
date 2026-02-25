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
        int stackPointer = -1;
        char[] stack = new char[input.length()];
        String openingBrackets = "({[";
        String closingBrackets = ")}]";

        for (char ch : input.toCharArray()) {
            int openingIndex = openingBrackets.indexOf(ch);
            if (openingIndex != -1) {
                stack[++stackPointer] = ch;
            } else {
                if (stackPointer >= 0) {
                    char top = stack[stackPointer];
                    int topOpeningIndex = openingBrackets.indexOf(top);
                    int closingIndex = closingBrackets.indexOf(ch);
                    if (topOpeningIndex == closingIndex) {
                        stackPointer--;
                        continue;
                    }
                }
                return false;
            }
        }
        return stackPointer == -1;
    }
}