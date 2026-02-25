package com.thealgorithms.stacks;

import java.util.Stack;


public final class PrefixToInfix {
    private PrefixToInfix() {
    }


    public static boolean isOperator(char token) {
        return token == '+' || token == '-' || token == '/' || token == '*' || token == '^';
    }


    public static String getPrefixToInfix(String prefix) {
        if (prefix == null) {
            throw new NullPointerException("Null prefix expression");
        }
        if (prefix.isEmpty()) {
            return "";
        }

        Stack<String> stack = new Stack<>();

        for (int i = prefix.length() - 1; i >= 0; i--) {
            char token = prefix.charAt(i);

            if (isOperator(token)) {
                String operandA = stack.pop();
                String operandB = stack.pop();

                String infix = "(" + operandA + token + operandB + ")";

                stack.push(infix);
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
