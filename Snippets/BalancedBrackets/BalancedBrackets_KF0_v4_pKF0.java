package com.thealgorithms.stacks;

import java.util.Deque;
import java.util.Map;
import java.util.Set;
import java.util.ArrayDeque;

/**
 * The nested brackets problem determines if a sequence of brackets is properly nested.
 *
 * A sequence of brackets s is considered properly nested if any of the following conditions are true:
 * - s is empty
 * - s has the form (U) or [U] or {U} where U is a properly nested string
 * - s has the form VW where V and W are properly nested strings
 *
 * For example, the string "()()[()]" is properly nested but "[(()]" is not.
 * The function {@code isBalanced} takes as input a string S which is a sequence of brackets and
 * returns true if S is nested and false otherwise.
 *
 * @author akshay sharma
 * @author <a href="https://github.com/khalil2535">khalil2535<a>
 * @author shellhub
 */
final class BalancedBrackets {

    private static final Map<Character, Character> OPEN_TO_CLOSE = Map.of(
        '(', ')',
        '[', ']',
        '{', '}',
        '<', '>'
    );

    private static final Set<Character> OPEN_BRACKETS = OPEN_TO_CLOSE.keySet();
    private static final Set<Character> CLOSE_BRACKETS = Set.copyOf(OPEN_TO_CLOSE.values());

    private BalancedBrackets() {
        // Utility class; prevent instantiation
    }

    /**
     * Check if {@code leftBracket} and {@code rightBracket} are a matching pair.
     *
     * @param leftBracket  left bracket
     * @param rightBracket right bracket
     * @return {@code true} if {@code leftBracket} and {@code rightBracket} are paired, otherwise {@code false}
     */
    public static boolean isPaired(char leftBracket, char rightBracket) {
        return OPEN_TO_CLOSE.getOrDefault(leftBracket, '\0') == rightBracket;
    }

    /**
     * Check if {@code brackets} is balanced.
     *
     * @param brackets the brackets string
     * @return {@code true} if {@code brackets} is balanced, otherwise {@code false}
     */
    public static boolean isBalanced(String brackets) {
        if (brackets == null) {
            throw new IllegalArgumentException("brackets is null");
        }

        Deque<Character> stack = new ArrayDeque<>();

        for (char ch : brackets.toCharArray()) {
            if (OPEN_BRACKETS.contains(ch)) {
                stack.push(ch);
                continue;
            }

            if (CLOSE_BRACKETS.contains(ch)) {
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