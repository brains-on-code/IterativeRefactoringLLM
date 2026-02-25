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
     * @param character the character to check
     * @return true if the character is an operator, false otherwise
     */
    public static boolean isOperator(char character) {
        return character == '+' || character == '-' || character == '/' || character == '*' || character == '^';
    }

    /**
     * Validates whether the given string is a valid postfix expression.
     * Operands are assumed to be single alphabetic characters.
     *
     * @param postfixExpression the postfix expression to validate
     * @return true if the expression is valid, false otherwise
     */
    public static boolean isValidPostfixExpression(String postfixExpression) {
        if (postfixExpression.length() == 1 && Character.isAlphabetic(postfixExpression.charAt(0))) {
            return true;
        }

        if (postfixExpression.length() < 3) {
            return false;
        }

        int operandCount = 0;
        int operatorCount = 0;

        for (char character : postfixExpression.toCharArray()) {
            if (isOperator(character)) {
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
        StringBuilder infixExpressionBuilder = new StringBuilder();

        for (char character : postfixExpression.toCharArray()) {
            if (!isOperator(character)) {
                operandStack.push(Character.toString(character));
            } else {
                String rightOperand = operandStack.pop();
                String leftOperand = operandStack.pop();
                infixExpressionBuilder
                    .append('(')
                    .append(leftOperand)
                    .append(character)
                    .append(rightOperand)
                    .append(')');
                operandStack.push(infixExpressionBuilder.toString());
                infixExpressionBuilder.setLength(0);
            }
        }

        return operandStack.pop();
    }
}