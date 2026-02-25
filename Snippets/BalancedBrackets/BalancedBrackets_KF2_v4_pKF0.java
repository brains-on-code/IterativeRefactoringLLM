package com.thealgorithms.stacks;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

final class BalancedBrackets {

    private static final Map<Character, Character> BRACKET_PAIRS = Map.of(
        '(', ')',
        '[', ']',
        '{', '}',
        '<', '>'
    );

    private static final Set<Character> OPENING_BRACKETS = BRACKET_PAIRS.keySet();
    private static final Set<Character> CLOSING_BRACKETS = new HashSet<>(BRACKET_PAIRS.values());

    private BalancedBrackets() {
        // Utility class; prevent instantiation
    }

    public static boolean isBalanced(String input) {
        validateInput(input);

        Stack<Character> stack = new Stack<>();

        for (char ch : input.toCharArray()) {
            if (isOpeningBracket(ch)) {
                stack.push(ch);
                continue;
            }

            if (isClosingBracket(ch)) {
                if (!matchesTopOfStack(stack, ch)) {
                    return false;
                }
                continue;
            }

            return false;
        }

        return stack.isEmpty();
    }

    private static void validateInput(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
    }

    private static boolean isOpeningBracket(char ch) {
        return OPENING_BRACKETS.contains(ch);
    }

    private static boolean isClosingBracket(char ch) {
        return CLOSING_BRACKETS.contains(ch);
    }

    private static boolean matchesTopOfStack(Stack<Character> stack, char closingBracket) {
        if (stack.isEmpty()) {
            return false;
        }
        char openingBracket = stack.pop();
        return isMatchingPair(openingBracket, closingBracket);
    }

    private static boolean isMatchingPair(char openingBracket, char closingBracket) {
        return BRACKET_PAIRS.getOrDefault(openingBracket, '\0') == closingBracket;
    }
}