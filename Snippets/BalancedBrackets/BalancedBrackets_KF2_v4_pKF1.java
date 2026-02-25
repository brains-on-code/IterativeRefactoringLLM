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
            char openingBracketType = bracketPair[0];
            char closingBracketType = bracketPair[1];
            if (openingBracketType == openingBracket && closingBracketType == closingBracket) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBalanced(String bracketSequence) {
        if (bracketSequence == null) {
            throw new IllegalArgumentException("input is null");
        }

        Stack<Character> bracketStack = new Stack<>();

        for (char bracket : bracketSequence.toCharArray()) {
            switch (bracket) {
                case '(':
                case '[':
                case '<':
                case '{':
                    bracketStack.push(bracket);
                    break;
                case ')':
                case ']':
                case '>':
                case '}':
                    if (bracketStack.isEmpty()
                            || !isMatchingPair(bracketStack.pop(), bracket)) {
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