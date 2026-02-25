package com.thealgorithms.stacks;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

    private static final Set<Character> OPENING_BRACKETS = BRACKET_PAIRS.keySet();
    private static final Set<Character> CLOSING_BRACKETS = new HashSet<>(BRACKET_PAIRS.values());

    private BracketValidator() {
        // Prevent instantiation
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
            if (OPENING_BRACKETS.contains(ch)) {
                stack.push(ch);
            } else if (CLOSING_BRACKETS.contains(ch)) {
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