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
            if (bracketPair[0] == openingBracket && bracketPair[1] == closingBracket) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates whether the given string consists of properly balanced and
     * correctly ordered brackets.
     *
     * @param bracketSequence the string containing bracket characters
     * @return true if the brackets are balanced; false otherwise
     * @throws IllegalArgumentException if the input string is null
     */
    public static boolean isBalanced(String bracketSequence) {
        if (bracketSequence == null) {
            throw new IllegalArgumentException("bracketSequence is null");
        }

        Stack<Character> bracketStack = new Stack<>();

        for (char currentChar : bracketSequence.toCharArray()) {
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