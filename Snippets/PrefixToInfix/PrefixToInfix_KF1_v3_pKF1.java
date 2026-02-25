package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for converting prefix expressions to infix expressions.
 */
public final class PrefixToInfixConverter {

    private PrefixToInfixConverter() {
    }

    /**
     * Checks whether the given character is a supported operator.
     *
     * @param ch the character to check
     * @return true if the character is an operator; false otherwise
     */
    public static boolean isOperator(char ch) {
        return ch == '+'
            || ch == '-'
            || ch == '/'
            || ch == '*'
            || ch == '^';
    }

    /**
     * Converts a prefix expression to its corresponding infix expression.
     *
     * @param prefixExpression the prefix expression to convert
     * @return the resulting infix expression
     * @throws NullPointerException if the prefix expression is null
     * @throws ArithmeticException  if the prefix expression is malformed
     */
    public static String convertPrefixToInfix(String prefixExpression) {
        if (prefixExpression == null) {
            throw new NullPointerException("Prefix expression must not be null");
        }
        if (prefixExpression.isEmpty()) {
            return "";
        }

        Stack<String> expressionStack = new Stack<>();

        for (int i = prefixExpression.length() - 1; i >= 0; i--) {
            char currentChar = prefixExpression.charAt(i);

            if (isOperator(currentChar)) {
                String leftExpression = expressionStack.pop();
                String rightExpression = expressionStack.pop();
                String combinedExpression =
                    "(" + leftExpression + currentChar + rightExpression + ")";
                expressionStack.push(combinedExpression);
            } else {
                expressionStack.push(Character.toString(currentChar));
            }
        }

        if (expressionStack.size() != 1) {
            throw new ArithmeticException("Malformed prefix expression");
        }

        return expressionStack.pop();
    }
}