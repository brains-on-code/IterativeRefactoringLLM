package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for checking whether a string of brackets is properly nested.
 *
 * A string is considered properly nested if:
 * <ul>
 *   <li>It is empty, or</li>
 *   <li>It has the form (U), [U], {U}, or &lt;U&gt; where U is properly nested, or</li>
 *   <li>It has the form VW where V and W are properly nested strings.</li>
 * </ul>
 *
 * Examples:
 * <pre>
 *   "()()[()]"  -> true
 *   "[(()]"     -> false
 * </pre>
 */
final class BalancedBrackets {

    private BalancedBrackets() {
        // Utility class; prevent instantiation.
    }

    /**
     * Returns {@code true} if {@code leftBracket} and {@code rightBracket}
     * form a valid bracket pair.
     *
     * @param leftBracket  the opening bracket
     * @param rightBracket the closing bracket
     * @return {@code true} if the two characters form a valid pair;
     *         {@code false} otherwise
     */
    public static boolean isPaired(char leftBracket, char rightBracket) {
        char[][] pairedBrackets = {
            {'(', ')'},
            {'[', ']'},
            {'{', '}'},
            {'<', '>'},
        };

        for (char[] pair : pairedBrackets) {
            if (pair[0] == leftBracket && pair[1] == rightBracket) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns {@code true} if the given string of brackets is balanced.
     *
     * @param brackets the string containing only bracket characters
     * @return {@code true} if the string is balanced; {@code false} otherwise
     * @throws IllegalArgumentException if {@code brackets} is {@code null}
     */
    public static boolean isBalanced(String brackets) {
        if (brackets == null) {
            throw new IllegalArgumentException("brackets is null");
        }

        Stack<Character> stack = new Stack<>();

        for (char bracket : brackets.toCharArray()) {
            switch (bracket) {
                case '(':
                case '[':
                case '<':
                case '{':
                    stack.push(bracket);
                    break;

                case ')':
                case ']':
                case '>':
                case '}':
                    if (stack.isEmpty() || !isPaired(stack.pop(), bracket)) {
                        return false;
                    }
                    break;

                default:
                    // Non-bracket character found; not a valid input.
                    return false;
            }
        }

        return stack.isEmpty();
    }
}