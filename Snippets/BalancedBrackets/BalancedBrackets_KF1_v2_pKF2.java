package com.thealgorithms.stacks;

import java.util.Map;
import java.util.Stack;

/**
 * Utility class for validating bracket sequences.
 *
 * Supported bracket pairs:
 * - ()
 * - []
 * - {}
 * - <>
 */
final class BracketValidator {

    private static final Map<Character, Character> BRACKET_PAIRS = Map.of(
        '(', ')',
        '[', ']',
        '{', '}',
        '<', '>'
    );

    private BracketValidator() {
        // Prevent instantiation
    }

    /**
     * Checks whether the given characters form a valid opening/closing bracket pair.
     *
     * @param open  the opening bracket character
     * @param close the closing bracket character
     * @return {@code true} if {@code open} and {@code close} form a valid pair; {@code false} otherwise
     */
    public static boolean isMatchingPair(char open, char close) {
        return BRACKET_PAIRS.getOrDefault(open, '\0') == close;
    }

    /**
     * Validates that the input string is a well-formed sequence of brackets.
     * A sequence is well-formed if:
     * - Every opening bracket has a corresponding closing bracket of the same type.
     * - Brackets are properly nested.
     * - The string contains only supported bracket characters.
     *
     * @param brackets the string containing bracket characters to validate
     * @return {@code true} if the string is a valid bracket sequence; {@code false} otherwise
     * @throws IllegalArgumentException if {@code brackets} is {@code null}
     */
    public static boolean isValidSequence(String brackets) {
        if (brackets == null) {
            throw new IllegalArgumentException("brackets is null");
        }

        Stack<Character> stack = new Stack<>();

        for (char ch : brackets.toCharArray()) {
            if (BRACKET_PAIRS.containsKey(ch)) {
                stack.push(ch);
            } else if (BRACKET_PAIRS.containsValue(ch)) {
                if (stack.isEmpty() || !isMatchingPair(stack.pop(), ch)) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return stack.isEmpty();
    }
}