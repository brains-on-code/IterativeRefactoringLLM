package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Converts a prefix expression to an infix expression using a stack.
 *
 * The input prefix expression should consist of
 * valid operands (letters or digits) and operators (+, -, *, /, ^).
 * Parentheses are not required in the prefix string.
 */
public final class PrefixToInfix {

    private PrefixToInfix() {
        // Utility class; prevent instantiation
    }

    /**
     * Determines if a given character is a valid arithmetic operator.
     *
     * @param ch the character to check
     * @return true if the character is an operator, false otherwise
     */
    public static boolean isOperator(char ch) {
        return ch == '+'
            || ch == '-'
            || ch == '/'
            || ch == '*'
            || ch == '^';
    }

    /**
     * Converts a valid prefix expression to an infix expression.
     *
     * @param prefixExpression the prefix expression to convert
     * @return the equivalent infix expression
     * @throws NullPointerException if the prefix expression is null
     */
    public static String convertPrefixToInfix(String prefixExpression) {
        if (prefixExpression == null) {
            throw new NullPointerException("Null prefix expression");
        }
        if (prefixExpression.isEmpty()) {
            return "";
        }

        Stack<String> expressionStack = new Stack<>();

        // Iterate over the prefix expression from right to left
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