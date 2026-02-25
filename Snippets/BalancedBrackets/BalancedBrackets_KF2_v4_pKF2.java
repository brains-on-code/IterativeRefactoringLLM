package com.thealgorithms.stacks;

import java.util.Map;
import java.util.Stack;

final class BalancedBrackets {

    private static final Map<Character, Character> BRACKET_PAIRS = Map.of(
        '(', ')',
        '[', ']',
        '{', '}',
        '<', '>'
    );

    private BalancedBrackets() {
        // Prevent instantiation
    }

    public static boolean isPaired(char leftBracket, char rightBracket) {
        return BRACKET_PAIRS.getOrDefault(leftBracket, '\0') == rightBracket;
    }

    public static boolean isBalanced(String brackets) {
        if (brackets == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }

        Stack<Character> stack = new Stack<>();

        for (char ch : brackets.toCharArray()) {
            if (BRACKET_PAIRS.containsKey(ch)) {
                stack.push(ch);
                continue;
            }

            if (BRACKET_PAIRS.containsValue(ch)) {
                if (stack.isEmpty() || !isPaired(stack.pop(), ch)) {
                    return false;
                }
                continue;
            }

            return false;
        }

        return stack.isEmpty();
    }
}