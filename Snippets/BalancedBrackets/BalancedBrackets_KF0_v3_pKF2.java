package com.thealgorithms.stacks;

import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Utility class for checking whether a string of brackets is properly nested.
 *
 * A string is considered properly nested if:
 * <ul>
 *   <li>It is empty, or</li>
 *   <li>It has the form (U), [U], {U}, or &lt;U&gt; where U is properly nested, or</li>
 *   <li>It has the form VW where V and W are properly nested strings.</li>
 * </ul>
 *
 * Examples:
 * <pre>
 *   "()()[()]"  -> true
 *   "[(()]"     -> false
 * </pre>
 */
final class BalancedBrackets {

    private static final Map<Character, Character> BRACKET_PAIRS = Map.of(
        '(', ')',
        '[', ']',
        '{', '}',
        '<', '>'
    );

    private static final Set<Character> OPENING_BRACKETS = BRACKET_PAIRS.keySet();
    private static final Set<Character> CLOSING_BRACKETS = Set.copyOf(BRACKET_PAIRS.values());

    private BalancedBrackets() {
        // Utility class; prevent instantiation.
    }

    /**
     * Returns {@code true} if {@code leftBracket} and {@code rightBracket}
     * form a valid bracket pair.
     *
     * @param leftBracket  the opening bracket
     * @param rightBracket the closing bracket
     * @return {@code true} if the two characters form a valid pair;
     *         {@code false} otherwise
     */
    public static boolean isPaired(char leftBracket, char rightBracket) {
        return BRACKET_PAIRS.getOrDefault(leftBracket, '\0') == rightBracket;
    }

    /**
     * Returns {@code true} if the given string of brackets is balanced.
     *
     * @param brackets the string containing only bracket characters
     * @return {@code true} if the string is balanced; {@code false} otherwise
     * @throws IllegalArgumentException if {@code brackets} is {@code null}
     */
    public static boolean isBalanced(String brackets) {
        if (brackets == null) {
            throw new IllegalArgumentException("brackets is null");
        }

        Stack<Character> stack = new Stack<>();

        for (char ch : brackets.toCharArray()) {
            if (isOpeningBracket(ch)) {
                stack.push(ch);
            } else if (isClosingBracket(ch)) {
                if (!matchesTopOfStack(stack, ch)) {
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

    private static boolean matchesTopOfStack(Stack<Character> stack, char closingBracket) {
        return !stack.isEmpty() && isPaired(stack.pop(), closingBracket);
    }
}