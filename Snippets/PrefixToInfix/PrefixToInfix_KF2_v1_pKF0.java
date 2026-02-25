package com.thealgorithms.stacks;

import java.util.Stack;

public final class PrefixToInfix {

    private PrefixToInfix() {
        // Utility class; prevent instantiation
    }

    private static final String NULL_PREFIX_MESSAGE = "Null prefix expression";
    private static final String MALFORMED_PREFIX_MESSAGE = "Malformed prefix expression";

    public static boolean isOperator(char token) {
        return token == '+' || token == '-' || token == '/' || token == '*' || token == '^';
    }

    public static String getPrefixToInfix(String prefix) {
        if (prefix == null) {
            throw new NullPointerException(NULL_PREFIX_MESSAGE);
        }
        if (prefix.isEmpty()) {
            return "";
        }

        Stack<String> operands = new Stack<>();

        for (int i = prefix.length() - 1; i >= 0; i--) {
            char token = prefix.charAt(i);

            if (isOperator(token)) {
                if (operands.size() < 2) {
                    throw new ArithmeticException(MALFORMED_PREFIX_MESSAGE);
                }

                String leftOperand = operands.pop();
                String rightOperand = operands.pop();
                String infixExpression = "(" + leftOperand + token + rightOperand + ")";
                operands.push(infixExpression);
            } else {
                operands.push(String.valueOf(token));
            }
        }

        if (operands.size() != 1) {
            throw new ArithmeticException(MALFORMED_PREFIX_MESSAGE);
        }

        return operands.pop();
    }
}