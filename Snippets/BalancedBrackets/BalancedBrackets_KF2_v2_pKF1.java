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
            char opening = bracketPair[0];
            char closing = bracketPair[1];
            if (opening == openingBracket && closing == closingBracket) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBalanced(String expression) {
        if (expression == null) {
            throw new IllegalArgumentException("expression is null");
        }

        Stack<Character> openingBrackets = new Stack<>();

        for (char currentCharacter : expression.toCharArray()) {
            switch (currentCharacter) {
                case '(':
                case '[':
                case '<':
                case '{':
                    openingBrackets.push(currentCharacter);
                    break;
                case ')':
                case ']':
                case '>':
                case '}':
                    if (openingBrackets.isEmpty()
                            || !isMatchingPair(openingBrackets.pop(), currentCharacter)) {
                        return false;
                    }
                    break;
                default:
                    return false;
            }
        }

        return openingBrackets.isEmpty();
    }
}