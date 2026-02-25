package com.thealgorithms.strings;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Map;

public final class ValidParentheses {

    private static final Map<Character, Character> BRACKET_PAIRS = Map.of(
        ')', '(',
        '}', '{',
        ']', '['
    );

    private ValidParentheses() {
        // Utility class; prevent instantiation
    }

    public static boolean isValid(String s) {
        return isValidParentheses(s);
    }

    public static boolean isValidParentheses(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }

        Deque<Character> stack = new ArrayDeque<>();

        for (char ch : s.toCharArray()) {
            if (BRACKET_PAIRS.containsValue(ch)) {
                stack.push(ch);
            } else if (BRACKET_PAIRS.containsKey(ch)) {
                if (stack.isEmpty() || stack.pop() != BRACKET_PAIRS.get(ch)) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}