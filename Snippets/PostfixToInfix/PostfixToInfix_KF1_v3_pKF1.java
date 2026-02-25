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
     * @param postfix the postfix expression to validate
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
     * Converts a postfix expression to its equivalent infix expression.
     * Operands are assumed to be single alphabetic characters.
     *
     * @param postfix the postfix expression to convert
     * @return the corresponding infix expression
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
        StringBuilder infixBuilder = new StringBuilder();

        for (char ch : postfix.toCharArray()) {
            if (!isOperator(ch)) {
                operands.push(Character.toString(ch));
            } else {
                String rightOperand = operands.pop();
                String leftOperand = operands.pop();
                infixBuilder
                    .append('(')
                    .append(leftOperand)
                    .append(ch)
                    .append(rightOperand)
                    .append(')');
                operands.push(infixBuilder.toString());
                infixBuilder.setLength(0);
            }
        }

        return operands.pop();
    }
}