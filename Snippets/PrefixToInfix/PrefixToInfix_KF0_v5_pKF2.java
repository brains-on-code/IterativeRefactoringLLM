package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for converting prefix expressions to infix expressions.
 *
 * <p>The input prefix expression should consist of valid operands
 * (letters or digits) and operators (+, -, *, /, ^). Parentheses are
 * not required in the prefix string.</p>
 */
public final class PrefixToInfix {

    private PrefixToInfix() {
        // Prevent instantiation
    }

    /**
     * Checks whether the given character is a supported arithmetic operator.
     *
     * @param token the character to check
     * @return {@code true} if the character is an operator, {@code false} otherwise
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
     * <p>Example: {@code *+AB-CD} → {@code ((A+B)*(C-D))}</p>
     *
     * @param prefix the prefix expression to convert
     * @return the equivalent infix expression
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

        for (int i = prefix.length() - 1; i >= 0; i--) {
            char token = prefix.charAt(i);

            if (isOperator(token)) {
                String leftOperand = stack.pop();
                String rightOperand = stack.pop();
                String infix = "(" + leftOperand + token + rightOperand + ")";
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