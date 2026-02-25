package com.thealgorithms.stacks;

import java.util.Stack;

public final class PostfixToInfix {

    private PostfixToInfix() {
    }

    public static boolean isOperator(char character) {
        return character == '+'
                || character == '-'
                || character == '/'
                || character == '*'
                || character == '^';
    }

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

    public static String convertPostfixToInfix(String postfixExpression) {
        if (postfixExpression.isEmpty()) {
            return "";
        }

        if (!isValidPostfixExpression(postfixExpression)) {
            throw new IllegalArgumentException("Invalid Postfix Expression");
        }

        Stack<String> expressionStack = new Stack<>();
        StringBuilder infixBuilder = new StringBuilder();

        for (char character : postfixExpression.toCharArray()) {
            if (!isOperator(character)) {
                expressionStack.push(Character.toString(character));
            } else {
                String rightOperand = expressionStack.pop();
                String leftOperand = expressionStack.pop();
                infixBuilder
                        .append('(')
                        .append(leftOperand)
                        .append(character)
                        .append(rightOperand)
                        .append(')');
                expressionStack.push(infixBuilder.toString());
                infixBuilder.setLength(0);
            }
        }

        return expressionStack.pop();
    }
}