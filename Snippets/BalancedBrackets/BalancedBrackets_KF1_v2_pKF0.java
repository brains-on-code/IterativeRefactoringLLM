package com.thealgorithms.stacks;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Map;

/**
 * Utility class for validating bracket sequences.
 */
final class BracketValidator {

    private static final Map<Character, Character> BRACKET_PAIRS = Map.of(
        ')', '(',
        ']', '[',
        '}', '{',
        '>', '<'
    );

    private BracketValidator() {
        // Prevent instantiation
    }

    /**
     * Checks whether the given opening and closing characters form a valid
     * bracket pair.
     *
     * @param opening the opening bracket character
     * @param closing the closing bracket character
     * @return {@code true} if {@code opening} and {@code closing} form a valid pair;
     *         {@code false} otherwise
     */
    public static boolean isMatchingPair(char opening, char closing) {
        return BRACKET_PAIRS.getOrDefault(closing, '\0') == opening;
    }

    /**
     * Validates whether the given string consists of properly balanced and
     * correctly ordered brackets.
     *
     * @param brackets the string containing bracket characters
     * @return {@code true} if the string is a valid bracket sequence;
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code brackets} is {@code null}
     */
    public static boolean isValidBracketSequence(String brackets) {
        if (brackets == null) {
            throw new IllegalArgumentException("brackets is null");
        }

        Deque<Character> stack = new ArrayDeque<>();

        for (char ch : brackets.toCharArray()) {
            if (BRACKET_PAIRS.containsValue(ch)) {
                stack.push(ch);
            } else if (BRACKET_PAIRS.containsKey(ch)) {
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