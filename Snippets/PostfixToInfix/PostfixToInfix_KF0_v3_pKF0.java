package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Postfix to Infix implementation via Stack.
 *
 * Function: String getPostfixToInfix(String postfix)
 * Returns the Infix Expression for the given postfix parameter.
 *
 * Avoid using parentheses/brackets/braces for the postfix string.
 * Postfix Expressions don't require these.
 *
 * @author nikslyon19 (Nikhil Bisht)
 */
public final class PostfixToInfix {

    private static final String INVALID_POSTFIX_MESSAGE = "Invalid Postfix Expression";

    private PostfixToInfix() {
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
     * Validates whether a given string is a valid postfix expression.
     *
     * A valid postfix expression must meet these criteria:
     * 1. It should have at least one operator and two operands.
     * 2. The number of operands should always be greater than the number of
     *    operators at any point in the traversal.
     *
     * @param postfix the postfix expression string to validate
     * @return true if the expression is valid, false otherwise
     */
    public static boolean isValidPostfixExpression(String postfix) {
        if (postfix == null || postfix.isEmpty()) {
            return false;
        }

        if (postfix.length() == 1 && Character.isAlphabetic(postfix.charAt(0))) {
            return true;
        }

        // Postfix expression should have at least one operator and two operands
        if (postfix.length() < 3) {
            return false;
        }

        int operandCount = 0;
        int operatorCount = 0;

        for (char token : postfix.toCharArray()) {
            if (isOperator(token)) {
                operatorCount++;
                // Invalid: more operators than operands at any point
                if (operatorCount >= operandCount) {
                    return false;
                }
            } else {
                operandCount++;
            }
        }

        return operandCount == operatorCount + 1;
    }

    /**
     * Converts a valid postfix expression to an infix expression.
     *
     * @param postfix the postfix expression to convert
     * @return the equivalent infix expression
     * @throws IllegalArgumentException if the postfix expression is invalid
     */
    public static String getPostfixToInfix(String postfix) {
        if (postfix == null || postfix.isEmpty()) {
            return "";
        }

        if (!isValidPostfixExpression(postfix)) {
            throw new IllegalArgumentException(INVALID_POSTFIX_MESSAGE);
        }

        Stack<String> operands = new Stack<>();

        for (char token : postfix.toCharArray()) {
            if (!isOperator(token)) {
                operands.push(Character.toString(token));
                continue;
            }

            String rightOperand = operands.pop();
            String leftOperand = operands.pop();
            String expression = "(" + leftOperand + token + rightOperand + ")";
            operands.push(expression);
        }

        return operands.pop();
    }
}