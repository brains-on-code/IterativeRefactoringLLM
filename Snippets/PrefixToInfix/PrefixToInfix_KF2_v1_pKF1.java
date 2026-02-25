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
            throw new NullPointerException("Null prefix expression");
        }
        if (prefixExpression.isEmpty()) {
            return "";
        }

        Stack<String> expressionStack = new Stack<>();

        for (int index = prefixExpression.length() - 1; index >= 0; index--) {
            char currentChar = prefixExpression.charAt(index);

            if (isOperator(currentChar)) {
                String leftOperand = expressionStack.pop();
                String rightOperand = expressionStack.pop();
                String infixExpression = "(" + leftOperand + currentChar + rightOperand + ")";
                expressionStack.push(infixExpression);
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