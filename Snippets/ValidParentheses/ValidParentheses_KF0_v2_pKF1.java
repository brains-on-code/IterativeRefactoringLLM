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

        for (char bracket : input.toCharArray()) {
            switch (bracket) {
                case '{':
                case '[':
                case '(':
                    bracketStack[stackSize++] = bracket;
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
                    throw new IllegalArgumentException("Unexpected character: " + bracket);
            }
        }
        return stackSize == 0;
    }

    public static boolean isValidParentheses(String input) {
        char[] bracketStack = new char[input.length()];
        int topIndex = -1;

        String openingBrackets = "({[";
        String closingBrackets = ")}]";

        for (char bracket : input.toCharArray()) {
            if (openingBrackets.indexOf(bracket) != -1) {
                bracketStack[++topIndex] = bracket;
            } else {
                if (topIndex >= 0
                        && openingBrackets.indexOf(bracketStack[topIndex])
                                == closingBrackets.indexOf(bracket)) {
                    topIndex--;
                } else {
                    return false;
                }
            }
        }
        return topIndex == -1;
    }
}