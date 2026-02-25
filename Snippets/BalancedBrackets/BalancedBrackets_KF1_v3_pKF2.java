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
        // Utility class; prevent instantiation
    }

    /**
     * Returns {@code true} if {@code open} and {@code close} form a supported bracket pair.
     */
    public static boolean isMatchingPair(char open, char close) {
        return BRACKET_PAIRS.getOrDefault(open, '\0') == close;
    }

    /**
     * Returns {@code true} if {@code brackets} is a well-formed sequence of supported brackets.
     *
     * A sequence is well-formed if:
     * - Every opening bracket has a corresponding closing bracket of the same type.
     * - Brackets are properly nested.
     * - The string contains only supported bracket characters.
     *
     * @throws IllegalArgumentException if {@code brackets} is {@code null}
     */
    public static boolean isValidSequence(String brackets) {
        if (brackets == null) {
            throw new IllegalArgumentException("brackets is null");
        }

        Stack<Character> stack = new Stack<>();

        for (char ch : brackets.toCharArray()) {
            if (BRACKET_PAIRS.containsKey(ch)) {
                // Opening bracket: push onto stack
                stack.push(ch);
            } else if (BRACKET_PAIRS.containsValue(ch)) {
                // Closing bracket: stack must not be empty and must match top
                if (stack.isEmpty() || !isMatchingPair(stack.pop(), ch)) {
                    return false;
                }
            } else {
                // Unsupported character
                return false;
            }
        }

        // Valid if no unmatched opening brackets remain
        return stack.isEmpty();
    }
}