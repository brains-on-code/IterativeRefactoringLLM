package com.thealgorithms.stacks;

import java.util.Stack;

public final class PostfixToInfix {

    private PostfixToInfix() {
        // Utility class; prevent instantiation
    }

    private static final String OPERATORS = "+-/*^";

    public static boolean isOperator(char token) {
        return OPERATORS.indexOf(token) >= 0;
    }

    public static boolean isValidPostfixExpression(String postfix) {
        if (postfix == null || postfix.isEmpty()) {
            return false;
        }

        if (postfix.length() == 1 && Character.isAlphabetic(postfix.charAt(0))) {
            return true;
        }

        if (postfix.length() < 3) {
            return false;
        }

        int operandCount = 0;
        int operatorCount = 0;

        for (char token : postfix.toCharArray()) {
            if (isOperator(token)) {
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

    public static String getPostfixToInfix(String postfix) {
        if (postfix == null || postfix.isEmpty()) {
            return "";
        }

        if (!isValidPostfixExpression(postfix)) {
            throw new IllegalArgumentException("Invalid Postfix Expression");
        }

        Stack<String> stack = new Stack<>();

        for (char token : postfix.toCharArray()) {
            if (!isOperator(token)) {
                stack.push(Character.toString(token));
            } else {
                String rightOperand = stack.pop();
                String leftOperand = stack.pop();
                String expression = "(" + leftOperand + token + rightOperand + ")";
                stack.push(expression);
            }
        }

        return stack.pop();
    }
}