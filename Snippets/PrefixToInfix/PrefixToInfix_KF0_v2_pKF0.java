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

    private static final String NULL_PREFIX_MESSAGE = "Null prefix expression";
    private static final String MALFORMED_PREFIX_MESSAGE = "Malformed prefix expression";

    private PrefixToInfix() {
        // Utility class; prevent instantiation
    }

    /**
     * Determines if a given character is a valid arithmetic operator.
     *
     * @param token the character to check
     * @return true if the character is an operator, false otherwise
     */
    public static boolean isOperator(char token) {
        return token == '+'
            || token == '-'
            || token == '/'
            || token == '*'
            || token == '^';
    }

    /**
     * Converts a valid prefix expression to an infix expression.
     *
     * @param prefix the prefix expression to convert
     * @return the equivalent infix expression
     * @throws NullPointerException if the prefix expression is null
     * @throws ArithmeticException  if the prefix expression is malformed
     */
    public static String getPrefixToInfix(String prefix) {
        if (prefix == null) {
            throw new NullPointerException(NULL_PREFIX_MESSAGE);
        }
        if (prefix.isEmpty()) {
            return "";
        }

        Stack<String> expressionStack = new Stack<>();

        for (int i = prefix.length() - 1; i >= 0; i--) {
            char token = prefix.charAt(i);

            if (isOperator(token)) {
                processOperator(expressionStack, token);
            } else {
                expressionStack.push(Character.toString(token));
            }
        }

        if (expressionStack.size() != 1) {
            throw new ArithmeticException(MALFORMED_PREFIX_MESSAGE);
        }

        return expressionStack.pop();
    }

    private static void processOperator(Stack<String> expressionStack, char operator) {
        if (expressionStack.size() < 2) {
            throw new ArithmeticException(MALFORMED_PREFIX_MESSAGE);
        }

        String leftOperand = expressionStack.pop();
        String rightOperand = expressionStack.pop();
        String infixExpression = "(" + leftOperand + operator + rightOperand + ")";
        expressionStack.push(infixExpression);
    }
}