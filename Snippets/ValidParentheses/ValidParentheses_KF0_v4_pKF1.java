package com.thealgorithms.strings;

// Given a string containing just the characters '(', ')', '{', '}', '[' and ']',
// determine if the input string is valid. An input string is valid if:
// - Open brackets must be closed by the same type of brackets.
// - Open brackets must be closed in the correct order.
// - Every close bracket has a corresponding open bracket of the same type.
public final class ValidParentheses {

    private ValidParentheses() {}

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
        char[] bracketStack = new char[input.length()];
        int topIndex = -1;

        String openingBrackets = "({[";
        String closingBrackets = ")}]";

        for (char currentChar : input.toCharArray()) {
            if (openingBrackets.indexOf(currentChar) != -1) {
                bracketStack[++topIndex] = currentChar;
            } else {
                if (topIndex >= 0
                        && openingBrackets.indexOf(bracketStack[topIndex])
                                == closingBrackets.indexOf(currentChar)) {
                    topIndex--;
                } else {
                    return false;
                }
            }
        }
        return topIndex == -1;
    }
}