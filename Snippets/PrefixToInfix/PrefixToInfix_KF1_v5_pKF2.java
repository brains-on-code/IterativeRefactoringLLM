package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for working with prefix expressions.
 *
 * Currently supports conversion from prefix to infix notation.
 */
public final class PrefixExpressionUtils {

    private PrefixExpressionUtils() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether the given character is a supported operator.
     *
     * Supported operators: +, -, *, /, ^
     *
     * @param ch the character to check
     * @return {@code true} if {@code ch} is an operator; {@code false} otherwise
     */
    public static boolean isOperator(char ch) {
        return ch == '+'
            || ch == '-'
            || ch == '/'
            || ch == '*'
            || ch == '^';
    }

    /**
     * Converts a prefix expression to its equivalent infix expression.
     *
     * Example:
     * <pre>
     *   Input:  "*+AB-CD"
     *   Output: "((A+B)*(C-D))"
     * </pre>
     *
     * @param prefix the prefix expression
     * @return the corresponding infix expression
     * @throws NullPointerException if {@code prefix} is {@code null}
     * @throws ArithmeticException  if the prefix expression is malformed
     */
    public static String prefixToInfix(String prefix) {
        if (prefix == null) {
            throw new NullPointerException("Null prefix expression");
        }
        if (prefix.isEmpty()) {
            return "";
        }

        Stack<String> operands = new Stack<>();

        // Traverse from right to left for prefix processing
        for (int i = prefix.length() - 1; i >= 0; i--) {
            char currentChar = prefix.charAt(i);

            if (isOperator(currentChar)) {
                if (operands.size() < 2) {
                    throw new ArithmeticException("Malformed prefix expression");
                }

                String leftOperand = operands.pop();
                String rightOperand = operands.pop();
                String combinedExpression = "(" + leftOperand + currentChar + rightOperand + ")";
                operands.push(combinedExpression);
            } else {
                operands.push(Character.toString(currentChar));
            }
        }

        if (operands.size() != 1) {
            throw new ArithmeticException("Malformed prefix expression");
        }

        return operands.pop();
    }
}