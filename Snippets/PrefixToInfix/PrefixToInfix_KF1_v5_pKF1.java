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
     * @param symbol the character to check
     * @return true if the character is an operator; false otherwise
     */
    public static boolean isOperator(char symbol) {
        return symbol == '+'
            || symbol == '-'
            || symbol == '/'
            || symbol == '*'
            || symbol == '^';
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

        for (int position = prefixExpression.length() - 1; position >= 0; position--) {
            char currentSymbol = prefixExpression.charAt(position);

            if (isOperator(currentSymbol)) {
                String leftExpression = expressionStack.pop();
                String rightExpression = expressionStack.pop();
                String combinedExpression =
                    "(" + leftExpression + currentSymbol + rightExpression + ")";
                expressionStack.push(combinedExpression);
            } else {
                expressionStack.push(Character.toString(currentSymbol));
            }
        }

        if (expressionStack.size() != 1) {
            throw new ArithmeticException("Malformed prefix expression");
        }

        return expressionStack.pop();
    }
}