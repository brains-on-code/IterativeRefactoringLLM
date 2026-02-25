package com.thealgorithms.stacks;

import java.util.Stack;

final class BalancedBrackets {

    private BalancedBrackets() {
    }

    private static final char[][] BRACKET_PAIRS = {
        {'(', ')'},
        {'[', ']'},
        {'{', '}'},
        {'<', '>'},
    };

    public static boolean isMatchingPair(char openingBracket, char closingBracket) {
        for (char[] bracketPair : BRACKET_PAIRS) {
            if (bracketPair[0] == openingBracket && bracketPair[1] == closingBracket) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBalanced(String input) {
        if (input == null) {
            throw new IllegalArgumentException("input is null");
        }

        Stack<Character> bracketStack = new Stack<>();

        for (char currentChar : input.toCharArray()) {
            switch (currentChar) {
                case '(':
                case '[':
                case '<':
                case '{':
                    bracketStack.push(currentChar);
                    break;
                case ')':
                case ']':
                case '>':
                case '}':
                    if (bracketStack.isEmpty() || !isMatchingPair(bracketStack.pop(), currentChar)) {
                        return false;
                    }
                    break;
                default:
                    return false;
            }
        }

        return bracketStack.isEmpty();
    }
}