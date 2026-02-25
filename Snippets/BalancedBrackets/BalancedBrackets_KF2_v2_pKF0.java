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

    public static boolean isPaired(char leftBracket, char rightBracket) {
        return BRACKET_PAIRS.getOrDefault(leftBracket, '\0') == rightBracket;
    }

    public static boolean isBalanced(String brackets) {
        if (brackets == null) {
            throw new IllegalArgumentException("brackets is null");
        }

        Stack<Character> stack = new Stack<>();

        for (char ch : brackets.toCharArray()) {
            if (isOpeningBracket(ch)) {
                stack.push(ch);
            } else if (isClosingBracket(ch)) {
                if (!canPairWithTopOfStack(stack, ch)) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return stack.isEmpty();
    }

    private static boolean isOpeningBracket(char ch) {
        return OPENING_BRACKETS.contains(ch);
    }

    private static boolean isClosingBracket(char ch) {
        return CLOSING_BRACKETS.contains(ch);
    }

    private static boolean canPairWithTopOfStack(Stack<Character> stack, char closingBracket) {
        if (stack.isEmpty()) {
            return false;
        }
        char openingBracket = stack.pop();
        return isPaired(openingBracket, closingBracket);
    }
}