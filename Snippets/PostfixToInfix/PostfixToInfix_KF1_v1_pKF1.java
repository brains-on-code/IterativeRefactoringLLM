package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for working with postfix expressions.
 */
public final class PostfixConverter {

    private PostfixConverter() {
    }

    /**
     * Checks whether the given character is a supported operator.
     *
     * @param ch the character to check
     * @return true if the character is an operator, false otherwise
     */
    public static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '/' || ch == '*' || ch == '^';
    }

    /**
     * Validates whether the given string is a valid postfix expression.
     * Operands are assumed to be single alphabetic characters.
     *
     * @param expression the postfix expression to validate
     * @return true if the expression is valid, false otherwise
     */
    public static boolean isValidPostfixExpression(String expression) {
        if (expression.length() == 1 && Character.isAlphabetic(expression.charAt(0))) {
            return true;
        }

        if (expression.length() < 3) {
            return false;
        }

        int operandCount = 0;
        int operatorCount = 0;

        for (char ch : expression.toCharArray()) {
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
     * Converts a postfix expression to its equivalent infix expression.
     * Operands are assumed to be single alphabetic characters.
     *
     * @param postfixExpression the postfix expression to convert
     * @return the corresponding infix expression
     * @throws IllegalArgumentException if the postfix expression is invalid
     */
    public static String convertPostfixToInfix(String postfixExpression) {
        if (postfixExpression.isEmpty()) {
            return "";
        }

        if (!isValidPostfixExpression(postfixExpression)) {
            throw new IllegalArgumentException("Invalid Postfix Expression");
        }

        Stack<String> operandStack = new Stack<>();
        StringBuilder expressionBuilder = new StringBuilder();

        for (char ch : postfixExpression.toCharArray()) {
            if (!isOperator(ch)) {
                operandStack.push(Character.toString(ch));
            } else {
                String rightOperand = operandStack.pop();
                String leftOperand = operandStack.pop();
                expressionBuilder
                    .append('(')
                    .append(leftOperand)
                    .append(ch)
                    .append(rightOperand)
                    .append(')');
                operandStack.push(expressionBuilder.toString());
                expressionBuilder.setLength(0);
            }
        }

        return operandStack.pop();
    }
}