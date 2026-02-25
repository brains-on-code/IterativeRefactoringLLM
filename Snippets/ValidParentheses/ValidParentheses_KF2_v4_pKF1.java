package com.thealgorithms.strings;

public final class ValidParentheses {

    private ValidParentheses() {
    }

    public static boolean isValid(String input) {
        char[] bracketStack = new char[input.length()];
        int stackSize = 0;

        for (char currentChar : input.toCharArray()) {
            switch (currentChar) {
                case '{':
                case '[':
                case '(':
                    bracketStack[stackSize++] = currentChar;
                    break;
                case '}':
                    if (stackSize == 0 || bracketStack[--stackSize] != '{') {
                        return false;
                    }
                    break;
                case ')':
                    if (stackSize == 0 || bracketStack[--stackSize] != '(') {
                        return false;
                    }
                    break;
                case ']':
                    if (stackSize == 0 || bracketStack[--stackSize] != '[') {
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
        int stackTopIndex = -1;
        char[] bracketStack = new char[input.length()];
        String openingBrackets = "({[";
        String closingBrackets = ")}]";

        for (char currentChar : input.toCharArray()) {
            if (openingBrackets.indexOf(currentChar) != -1) {
                bracketStack[++stackTopIndex] = currentChar;
            } else {
                if (stackTopIndex >= 0
                        && openingBrackets.indexOf(bracketStack[stackTopIndex])
                                == closingBrackets.indexOf(currentChar)) {
                    stackTopIndex--;
                } else {
                    return false;
                }
            }
        }
        return stackTopIndex == -1;
    }
}