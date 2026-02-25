package com.thealgorithms.stacks;

import java.util.Stack;

public final class PrefixToInfix {

    private PrefixToInfix() {
    }

    private static boolean isOperator(char symbol) {
        return symbol == '+'
            || symbol == '-'
            || symbol == '/'
            || symbol == '*'
            || symbol == '^';
    }

    public static String convertPrefixToInfix(String prefixExpression) {
        if (prefixExpression == null) {
            throw new NullPointerException("Null prefix expression");
        }
        if (prefixExpression.isEmpty()) {
            return "";
        }

        Stack<String> operandStack = new Stack<>();

        for (int i = prefixExpression.length() - 1; i >= 0; i--) {
            char currentSymbol = prefixExpression.charAt(i);

            if (isOperator(currentSymbol)) {
                String leftOperand = operandStack.pop();
                String rightOperand = operandStack.pop();
                String combinedExpression = "(" + leftOperand + currentSymbol + rightOperand + ")";
                operandStack.push(combinedExpression);
            } else {
                operandStack.push(Character.toString(currentSymbol));
            }
        }

        if (operandStack.size() != 1) {
            throw new ArithmeticException("Malformed prefix expression");
        }

        return operandStack.pop();
    }
}