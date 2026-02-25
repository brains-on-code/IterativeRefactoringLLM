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
        char[][] validBracketPairs = {
            {'(', ')'},
            {'[', ']'},
            {'{', '}'},
            {'<', '>'},
        };

        for (char[] bracketPair : validBracketPairs) {
            char pairOpeningBracket = bracketPair[0];
            char pairClosingBracket = bracketPair[1];

            if (pairOpeningBracket == openingBracket && pairClosingBracket == closingBracket) {
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

        Stack<Character> openingBracketsStack = new Stack<>();

        for (char currentCharacter : bracketSequence.toCharArray()) {
            switch (currentCharacter) {
                case '(':
                case '[':
                case '<':
                case '{':
                    openingBracketsStack.push(currentCharacter);
                    break;
                case ')':
                case ']':
                case '>':
                case '}':
                    if (openingBracketsStack.isEmpty()
                            || !isMatchingPair(openingBracketsStack.pop(), currentCharacter)) {
                        return false;
                    }
                    break;
                default:
                    return false;
            }
        }

        return openingBracketsStack.isEmpty();
    }
}