package com.thealgorithms.stacks;

import java.util.Stack;

public final class PrefixToInfix {

    private static final String NULL_PREFIX_MESSAGE = "Null prefix expression";
    private static final String MALFORMED_PREFIX_MESSAGE = "Malformed prefix expression";
    private static final String OPERATORS = "+-/*^";

    private PrefixToInfix() {
        // Utility class; prevent instantiation
    }

    private static boolean isOperator(char token) {
        return OPERATORS.indexOf(token) >= 0;
    }

    public static String getPrefixToInfix(String prefix) {
        if (prefix == null) {
            throw new NullPointerException(NULL_PREFIX_MESSAGE);
        }
        if (prefix.isEmpty()) {
            return "";
        }

        Stack<String> operandStack = new Stack<>();

        for (int index = prefix.length() - 1; index >= 0; index--) {
            char token = prefix.charAt(index);

            if (isOperator(token)) {
                if (operandStack.size() < 2) {
                    throw new ArithmeticException(MALFORMED_PREFIX_MESSAGE);
                }

                String leftOperand = operandStack.pop();
                String rightOperand = operandStack.pop();
                String combinedExpression = "(" + leftOperand + token + rightOperand + ")";
                operandStack.push(combinedExpression);
            } else {
                operandStack.push(String.valueOf(token));
            }
        }

        if (operandStack.size() != 1) {
            throw new ArithmeticException(MALFORMED_PREFIX_MESSAGE);
        }

        return operandStack.pop();
    }
}