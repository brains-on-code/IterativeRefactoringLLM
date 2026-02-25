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
     * @param opening the opening bracket character
     * @param closing the closing bracket character
     * @return true if the characters form a valid bracket pair; false otherwise
     */
    public static boolean isMatchingPair(char opening, char closing) {
        char[][] bracketPairs = {
            {'(', ')'},
            {'[', ']'},
            {'{', '}'},
            {'<', '>'},
        };

        for (char[] pair : bracketPairs) {
            if (pair[0] == opening && pair[1] == closing) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates whether the given string consists of properly balanced and
     * correctly ordered brackets.
     *
     * @param brackets the string containing bracket characters
     * @return true if the brackets are balanced; false otherwise
     * @throws IllegalArgumentException if the input string is null
     */
    public static boolean isBalanced(String brackets) {
        if (brackets == null) {
            throw new IllegalArgumentException("brackets is null");
        }

        Stack<Character> stack = new Stack<>();

        for (char ch : brackets.toCharArray()) {
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