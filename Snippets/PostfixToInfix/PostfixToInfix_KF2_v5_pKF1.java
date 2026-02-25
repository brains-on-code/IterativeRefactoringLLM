package com.thealgorithms.stacks;

import java.util.Stack;

public final class PostfixToInfix {

    private PostfixToInfix() {
    }

    private static boolean isOperator(char ch) {
        return ch == '+'
                || ch == '-'
                || ch == '/'
                || ch == '*'
                || ch == '^';
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

        for (char ch : postfixExpression.toCharArray()) {
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

    public static String convertPostfixToInfix(String postfixExpression) {
        if (postfixExpression.isEmpty()) {
            return "";
        }

        if (!isValidPostfixExpression(postfixExpression)) {
            throw new IllegalArgumentException("Invalid Postfix Expression");
        }

        Stack<String> operandStack = new Stack<>();
        StringBuilder infixBuilder = new StringBuilder();

        for (char ch : postfixExpression.toCharArray()) {
            if (!isOperator(ch)) {
                operandStack.push(Character.toString(ch));
            } else {
                String rightOperand = operandStack.pop();
                String leftOperand = operandStack.pop();
                infixBuilder
                        .append('(')
                        .append(leftOperand)
                        .append(ch)
                        .append(rightOperand)
                        .append(')');
                operandStack.push(infixBuilder.toString());
                infixBuilder.setLength(0);
            }
        }

        return operandStack.pop();
    }
}