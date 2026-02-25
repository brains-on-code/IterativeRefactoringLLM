package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * The nested brackets problem is a problem that determines if a sequence of
 * brackets are properly nested. A sequence of brackets s is considered properly
 * nested if any of the following conditions are true: - s is empty - s has the
 * form (U) or [U] or {U} where U is a properly nested string - s has the form
 * VW where V and W are properly nested strings For example, the string
 * "()()[()]" is properly nested but "[(()]" is not. The function called
 * isBalanced takes as input a string S which is a sequence of brackets and
 * returns true if S is nested and false otherwise.
 *
 * @author akshay sharma
 * @author <a href="https://github.com/khalil2535">khalil2535<a>
 * @author shellhub
 */
final class BalancedBrackets {

    private static final char[][] BRACKET_PAIRS = {
        {'(', ')'},
        {'[', ']'},
        {'{', '}'},
        {'<', '>'},
    };

    private BalancedBrackets() {
    }

    /**
     * Check if {@code openingBracket} and {@code closingBracket} form a valid pair.
     *
     * @param openingBracket opening bracket
     * @param closingBracket closing bracket
     * @return {@code true} if {@code openingBracket} and {@code closingBracket} form
     * a valid pair, otherwise {@code false}
     */
    public static boolean isPaired(char openingBracket, char closingBracket) {
        for (char[] bracketPair : BRACKET_PAIRS) {
            if (bracketPair[0] == openingBracket && bracketPair[1] == closingBracket) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if {@code bracketSequence} is balanced.
     *
     * @param bracketSequence the sequence of brackets
     * @return {@code true} if {@code bracketSequence} is balanced, otherwise
     * {@code false}
     */
    public static boolean isBalanced(String bracketSequence) {
        if (bracketSequence == null) {
            throw new IllegalArgumentException("bracketSequence is null");
        }

        Stack<Character> openingBrackets = new Stack<>();

        for (char currentChar : bracketSequence.toCharArray()) {
            switch (currentChar) {
                case '(':
                case '[':
                case '<':
                case '{':
                    openingBrackets.push(currentChar);
                    break;
                case ')':
                case ']':
                case '>':
                case '}':
                    if (openingBrackets.isEmpty()
                        || !isPaired(openingBrackets.pop(), currentChar)) {
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