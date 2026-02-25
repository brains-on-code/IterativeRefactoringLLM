package com.thealgorithms.strings;

public final class ParenthesesChecker {

    private ParenthesesChecker() {
    }

    public static boolean isBalancedUsingArray(String input) {
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

    public static boolean isBalancedUsingIndexMatch(String input) {
        int stackTopIndex = -1;
        char[] bracketStack = new char[input.length()];
        String openingBrackets = "({[";
        String closingBrackets = ")}]";

        for (char currentChar : input.toCharArray()) {
            int openingIndex = openingBrackets.indexOf(currentChar);
            if (openingIndex != -1) {
                bracketStack[++stackTopIndex] = currentChar;
            } else {
                if (stackTopIndex >= 0) {
                    char topBracket = bracketStack[stackTopIndex];
                    int topOpeningIndex = openingBrackets.indexOf(topBracket);
                    int closingIndex = closingBrackets.indexOf(currentChar);
                    if (topOpeningIndex == closingIndex) {
                        stackTopIndex--;
                        continue;
                    }
                }
                return false;
            }
        }
        return stackTopIndex == -1;
    }
}