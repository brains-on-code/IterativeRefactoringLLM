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

    public static boolean isBalanced(String input) {
        if (input == null) {
            throw new IllegalArgumentException("input is null");
        }

        Stack<Character> stack = new Stack<>();

        for (char ch : input.toCharArray()) {
            switch (ch) {
                case '(':
                case '[':
                case '<':
                case '{':
                    stack.push(ch);
                    break;
                case ')':
                case ']':
                case '>':
                case '}':
                    if (stack.isEmpty() || !isMatchingPair(stack.pop(), ch)) {
                        return false;
                    }
                    break;
                default:
                    return false;
            }
        }

        return stack.isEmpty();
    }
}