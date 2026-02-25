package com.thealgorithms.stacks;

import java.util.Stack;

public final class PostfixToInfix {

    private static final String OPERATORS = "+-/*^";

    private PostfixToInfix() {
        // Utility class; prevent instantiation
    }

    private static boolean isOperator(char token) {
        return OPERATORS.indexOf(token) >= 0;
    }

    public static boolean isValidPostfixExpression(String postfix) {
        if (postfix == null || postfix.isEmpty()) {
            return false;
        }

        int length = postfix.length();

        if (length == 1) {
            return Character.isAlphabetic(postfix.charAt(0));
        }

        if (length < 3) {
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

        Stack<String> operandStack = new Stack<>();

        for (char token : postfix.toCharArray()) {
            if (isOperator(token)) {
                String rightOperand = operandStack.pop();
                String leftOperand = operandStack.pop();
                String expression = "(" + leftOperand + token + rightOperand + ")";
                operandStack.push(expression);
            } else {
                operandStack.push(String.valueOf(token));
            }
        }

        return operandStack.pop();
    }
}