package com.thealgorithms.stacks;

import java.util.Stack;



public final class PostfixToInfix {
    private PostfixToInfix() {
    }


    public static boolean isOperator(char token) {
        return token == '+' || token == '-' || token == '/' || token == '*' || token == '^';
    }


    public static boolean isValidPostfixExpression(String postfix) {
        if (postfix.length() == 1 && (Character.isAlphabetic(postfix.charAt(0)))) {
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
        if (postfix.isEmpty()) {
            return "";
        }

        if (!isValidPostfixExpression(postfix)) {
            throw new IllegalArgumentException("Invalid Postfix Expression");
        }

        Stack<String> stack = new Stack<>();
        StringBuilder valueString = new StringBuilder();

        for (char token : postfix.toCharArray()) {
            if (!isOperator(token)) {
                stack.push(Character.toString(token));
            } else {
                String operandB = stack.pop();
                String operandA = stack.pop();
                valueString.append('(').append(operandA).append(token).append(operandB).append(')');
                stack.push(valueString.toString());
                valueString.setLength(0);
            }
        }

        return stack.pop();
    }
}
