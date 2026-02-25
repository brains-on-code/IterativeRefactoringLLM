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
     * Validates whether a given string is a valid postfix expression.
     *
     * A valid postfix expression must meet these criteria:
     * 1. It should have at least one operator and two operands.
     * 2. The number of operands should always be greater than the number of operators at any point in the traversal.
     *
     * @param postfix the postfix expression string to validate
     * @return true if the expression is valid, false otherwise
     */
    public static boolean isValidPostfixExpression(String postfix) {
        if (postfix.length() == 1 && Character.isAlphabetic(postfix.charAt(0))) {
            return true;
        }

        if (postfix.length() < 3) {
            return false;
        }

        int operandCount = 0;
        int operatorCount = 0;

        for (char ch : postfix.toCharArray()) {
            if (isOperator(ch)) {
                operatorCount++;
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
    public static String convertPostfixToInfix(String postfix) {
        if (postfix.isEmpty()) {
            return "";
        }

        if (!isValidPostfixExpression(postfix)) {
            throw new IllegalArgumentException("Invalid Postfix Expression");
        }

        Stack<String> operands = new Stack<>();
        StringBuilder currentExpression = new StringBuilder();

        for (char ch : postfix.toCharArray()) {
            if (!isOperator(ch)) {
                operands.push(Character.toString(ch));
            } else {
                String rightOperand = operands.pop();
                String leftOperand = operands.pop();
                currentExpression
                    .append('(')
                    .append(leftOperand)
                    .append(ch)
                    .append(rightOperand)
                    .append(')');
                operands.push(currentExpression.toString());
                currentExpression.setLength(0);
            }
        }

        return operands.pop();
    }
}