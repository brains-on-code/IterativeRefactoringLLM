package com.thealgorithms.stacks;

import java.util.Stack;

public final class PrefixToInfix {

    private PrefixToInfix() {
    }

    private static boolean isOperator(char character) {
        return character == '+'
            || character == '-'
            || character == '/'
            || character == '*'
            || character == '^';
    }

    public static String convertPrefixToInfix(String prefixExpression) {
        if (prefixExpression == null) {
            throw new NullPointerException("Prefix expression cannot be null");
        }
        if (prefixExpression.isEmpty()) {
            return "";
        }

        Stack<String> expressionStack = new Stack<>();

        for (int index = prefixExpression.length() - 1; index >= 0; index--) {
            char currentChar = prefixExpression.charAt(index);

            if (isOperator(currentChar)) {
                String leftExpression = expressionStack.pop();
                String rightExpression = expressionStack.pop();
                String combinedExpression = "(" + leftExpression + currentChar + rightExpression + ")";
                expressionStack.push(combinedExpression);
            } else {
                expressionStack.push(Character.toString(currentChar));
            }
        }

        if (expressionStack.size() != 1) {
            throw new ArithmeticException("Malformed prefix expression");
        }

        return expressionStack.pop();
    }
}