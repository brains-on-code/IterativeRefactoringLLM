package com.thealgorithms.stacks;

import java.util.Stack;

public final class PostfixToInfix {

    private PostfixToInfix() {
    }

    public static boolean isOperator(char symbol) {
        return symbol == '+'
                || symbol == '-'
                || symbol == '/'
                || symbol == '*'
                || symbol == '^';
    }

    public static boolean isValidPostfixExpression(String postfix) {
        if (postfix.length() == 1 && Character.isAlphabetic(postfix.charAt(0))) {
            return true;
        }

        if (postfix.length() < 3) {
            return false;
        }

        int operandCount = 0;
        int operatorCount = 0;

        for (char symbol : postfix.toCharArray()) {
            if (isOperator(symbol)) {
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

    public static String convertPostfixToInfix(String postfix) {
        if (postfix.isEmpty()) {
            return "";
        }

        if (!isValidPostfixExpression(postfix)) {
            throw new IllegalArgumentException("Invalid Postfix Expression");
        }

        Stack<String> operandStack = new Stack<>();
        StringBuilder currentExpression = new StringBuilder();

        for (char symbol : postfix.toCharArray()) {
            if (!isOperator(symbol)) {
                operandStack.push(Character.toString(symbol));
            } else {
                String rightOperand = operandStack.pop();
                String leftOperand = operandStack.pop();
                currentExpression
                        .append('(')
                        .append(leftOperand)
                        .append(symbol)
                        .append(rightOperand)
                        .append(')');
                operandStack.push(currentExpression.toString());
                currentExpression.setLength(0);
            }
        }

        return operandStack.pop();
    }
}