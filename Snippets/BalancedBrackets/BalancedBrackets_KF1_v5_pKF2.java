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
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether the given characters form a supported bracket pair.
     *
     * @param open  the opening bracket character
     * @param close the closing bracket character
     * @return {@code true} if {@code open} and {@code close} form a valid pair; {@code false} otherwise
     */
    public static boolean isMatchingPair(char open, char close) {
        return BRACKET_PAIRS.getOrDefault(open, '\0') == close;
    }

    /**
     * Validates that the given string is a well-formed sequence of supported brackets.
     *
     * A sequence is well-formed if:
     * <ul>
     *   <li>Every opening bracket has a corresponding closing bracket of the same type.</li>
     *   <li>Brackets are properly nested.</li>
     *   <li>The string contains only supported bracket characters.</li>
     * </ul>
     *
     * @param brackets the string containing only bracket characters to validate
     * @return {@code true} if the sequence is well-formed; {@code false} otherwise
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
                continue;
            }

            if (CLOSING_BRACKETS.contains(ch)) {
                if (stack.isEmpty() || !isMatchingPair(stack.pop(), ch)) {
                    return false;
                }
                continue;
            }

            // Unsupported character
            return false;
        }

        return stack.isEmpty();
    }
}