package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Postfix to Infix implementation via Stack
 *
 * Function: String convertPostfixToInfix(String postfixExpression)
 * Returns the Infix Expression for the given postfix parameter.
 *
 * Avoid using parentheses/brackets/braces for the postfix string.
 * Postfix Expressions don't require these.
 *
 * @author nikslyon19 (Nikhil Bisht)
 */
public final class PostfixToInfix {

    private PostfixToInfix() {
    }

    /**
     * Determines if a given character is a valid arithmetic operator.
     *
     * @param symbol the character to check
     * @return true if the character is an operator, false otherwise
     */
    public static boolean isOperator(char symbol) {
        return symbol == '+'
            || symbol == '-'
            || symbol == '/'
            || symbol == '*'
            || symbol == '^';
    }

    /**
     * Validates whether a given string is a valid postfix expression.
     *
     * A valid postfix expression must meet these criteria:
     * 1. It should have at least one operator and two operands.
     * 2. The number of operands should always be greater than the number of operators at any point in the traversal.
     *
     * @param postfixExpression the postfix expression string to validate
     * @return true if the expression is valid, false otherwise
     */
    public static boolean isValidPostfixExpression(String postfixExpression) {
        if (postfixExpression.length() == 1 && Character.isAlphabetic(postfixExpression.charAt(0))) {
            return true;
        }

        if (postfixExpression.length() < 3) {
            // Postfix expression should have at least one operator and two operands
            return false;
        }

        int operandCount = 0;
        int operatorCount = 0;

        for (char symbol : postfixExpression.toCharArray()) {
            if (isOperator(symbol)) {
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
     * @param postfixExpression the postfix expression to convert
     * @return the equivalent infix expression
     * @throws IllegalArgumentException if the postfix expression is invalid
     */
    public static String convertPostfixToInfix(String postfixExpression) {
        if (postfixExpression.isEmpty()) {
            return "";
        }

        if (!isValidPostfixExpression(postfixExpression)) {
            throw new IllegalArgumentException("Invalid Postfix Expression");
        }

        Stack<String> operands = new Stack<>();
        StringBuilder currentInfixExpression = new StringBuilder();

        for (char symbol : postfixExpression.toCharArray()) {
            if (!isOperator(symbol)) {
                operands.push(Character.toString(symbol));
            } else {
                String rightOperand = operands.pop();
                String leftOperand = operands.pop();
                currentInfixExpression
                    .append('(')
                    .append(leftOperand)
                    .append(symbol)
                    .append(rightOperand)
                    .append(')');
                operands.push(currentInfixExpression.toString());
                currentInfixExpression.setLength(0);
            }
        }

        return operands.pop();
    }
}