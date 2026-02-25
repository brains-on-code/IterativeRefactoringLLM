package com.thealgorithms.stacks;

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
final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether the given characters form a valid opening/closing bracket pair.
     *
     * @param open  the opening bracket character
     * @param close the closing bracket character
     * @return {@code true} if {@code open} and {@code close} form a valid pair; {@code false} otherwise
     */
    public static boolean method1(char open, char close) {
        char[][] bracketPairs = {
            {'(', ')'},
            {'[', ']'},
            {'{', '}'},
            {'<', '>'},
        };

        for (char[] pair : bracketPairs) {
            if (pair[0] == open && pair[1] == close) {
                return true;
            }
        }
        return false;
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
    public static boolean method2(String brackets) {
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
                    if (stack.isEmpty() || !method1(stack.pop(), ch)) {
                        return false;
                    }
                    break;
                default:
                    // Invalid character encountered
                    return false;
            }
        }

        return stack.isEmpty();
    }
}