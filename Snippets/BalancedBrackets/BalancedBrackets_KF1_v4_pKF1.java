package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for bracket-related operations.
 */
final class BracketUtils {

    private BracketUtils() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether the given opening and closing characters form a valid
     * bracket pair.
     *
     * @param openingBracket the opening bracket character
     * @param closingBracket the closing bracket character
     * @return true if the characters form a valid bracket pair; false otherwise
     */
    public static boolean isMatchingPair(char openingBracket, char closingBracket) {
        char[][] bracketPairs = {
            {'(', ')'},
            {'[', ']'},
            {'{', '}'},
            {'<', '>'},
        };

        for (char[] bracketPair : bracketPairs) {
            char pairOpening = bracketPair[0];
            char pairClosing = bracketPair[1];

            if (pairOpening == openingBracket && pairClosing == closingBracket) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates whether the given string consists of properly balanced and
     * correctly ordered brackets.
     *
     * @param input the string containing bracket characters
     * @return true if the brackets are balanced; false otherwise
     * @throws IllegalArgumentException if the input string is null
     */
    public static boolean isBalanced(String input) {
        if (input == null) {
            throw new IllegalArgumentException("input is null");
        }

        Stack<Character> openingBrackets = new Stack<>();

        for (char ch : input.toCharArray()) {
            switch (ch) {
                case '(':
                case '[':
                case '<':
                case '{':
                    openingBrackets.push(ch);
                    break;
                case ')':
                case ']':
                case '>':
                case '}':
                    if (openingBrackets.isEmpty()
                            || !isMatchingPair(openingBrackets.pop(), ch)) {
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