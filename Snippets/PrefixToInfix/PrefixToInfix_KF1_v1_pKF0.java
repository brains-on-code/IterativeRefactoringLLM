package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for converting prefix expressions to infix.
 */
public final class PrefixToInfixConverter {

    private PrefixToInfixConverter() {
        // Prevent instantiation
    }

    /**
     * Checks whether the given character is a supported operator.
     *
     * @param ch the character to check
     * @return true if the character is an operator; false otherwise
     */
    public static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '/' || ch == '*' || ch == '^';
    }

    /**
     * Converts a prefix expression to its infix representation.
     *
     * @param prefixExpression the prefix expression to convert
     * @return the infix representation of the given prefix expression
     * @throws NullPointerException   if {@code prefixExpression} is null
     * @throws ArithmeticException    if the prefix expression is malformed
     */
    public static String convertPrefixToInfix(String prefixExpression) {
        if (prefixExpression == null) {
            throw new NullPointerException("Null prefix expression");
        }
        if (prefixExpression.isEmpty()) {
            return "";
        }

        Stack<String> stack = new Stack<>();

        for (int i = prefixExpression.length() - 1; i >= 0; i--) {
            char ch = prefixExpression.charAt(i);

            if (isOperator(ch)) {
                String operand1 = stack.pop();
                String operand2 = stack.pop();
                String expression = "(" + operand1 + ch + operand2 + ")";
                stack.push(expression);
            } else {
                stack.push(Character.toString(ch));
            }
        }

        if (stack.size() != 1) {
            throw new ArithmeticException("Malformed prefix expression");
        }

        return stack.pop();
    }
}