package com.thealgorithms.stacks;

import java.util.Stack;

public final class PrefixToInfix {

    private PrefixToInfix() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether the given character is a supported operator.
     *
     * @param token the character to check
     * @return {@code true} if the character is an operator; {@code false} otherwise
     */
    public static boolean isOperator(char token) {
        return token == '+'
            || token == '-'
            || token == '/'
            || token == '*'
            || token == '^';
    }

    /**
     * Converts a prefix expression to its equivalent infix expression.
     *
     * @param prefix the prefix expression to convert
     * @return the corresponding infix expression
     * @throws NullPointerException if {@code prefix} is {@code null}
     * @throws ArithmeticException  if the prefix expression is malformed
     */
    public static String getPrefixToInfix(String prefix) {
        if (prefix == null) {
            throw new NullPointerException("Null prefix expression");
        }
        if (prefix.isEmpty()) {
            return "";
        }

        Stack<String> stack = new Stack<>();

        // Traverse the prefix expression from right to left
        for (int i = prefix.length() - 1; i >= 0; i--) {
            char token = prefix.charAt(i);

            if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new ArithmeticException("Malformed prefix expression");
                }

                String leftOperand = stack.pop();
                String rightOperand = stack.pop();
                String infixExpression = "(" + leftOperand + token + rightOperand + ")";
                stack.push(infixExpression);
            } else {
                stack.push(Character.toString(token));
            }
        }

        if (stack.size() != 1) {
            throw new ArithmeticException("Malformed prefix expression");
        }

        return stack.pop();
    }
}