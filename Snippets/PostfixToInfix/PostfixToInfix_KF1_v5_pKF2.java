package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility methods for working with postfix expressions.
 */
public final class PostfixUtils {

    private static final String SUPPORTED_OPERATORS = "+-/*^";

    private PostfixUtils() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns {@code true} if the given character is a supported operator.
     *
     * Supported operators: {@code +, -, /, *, ^}
     *
     * @param ch the character to test
     * @return {@code true} if {@code ch} is an operator; {@code false} otherwise
     */
    public static boolean isOperator(char ch) {
        return SUPPORTED_OPERATORS.indexOf(ch) >= 0;
    }

    /**
     * Returns {@code true} if the given string is a syntactically valid postfix expression.
     *
     * A valid postfix expression:
     * <ul>
     *     <li>Contains only single-letter alphabetic operands and supported operators</li>
     *     <li>Has at least 3 characters, unless it is a single operand</li>
     *     <li>Has exactly one more operand than operators</li>
     *     <li>Never has more operators than operands when scanned left to right</li>
     * </ul>
     *
     * @param expression the postfix expression to validate
     * @return {@code true} if the expression is valid; {@code false} otherwise
     */
    public static boolean isValidPostfix(String expression) {
        if (expression == null || expression.isEmpty()) {
            return false;
        }

        if (expression.length() == 1 && Character.isAlphabetic(expression.charAt(0))) {
            return true;
        }

        if (expression.length() < 3) {
            return false;
        }

        int operandCount = 0;
        int operatorCount = 0;

        for (char ch : expression.toCharArray()) {
            if (isOperator(ch)) {
                operatorCount++;
                // At any point, operators must be fewer than operands
                if (operatorCount >= operandCount) {
                    return false;
                }
            } else if (Character.isAlphabetic(ch)) {
                operandCount++;
            } else {
                // Any non-operator, non-alphabetic character is invalid
                return false;
            }
        }

        // In a valid postfix expression: operands = operators + 1
        return operandCount == operatorCount + 1;
    }

    /**
     * Converts a postfix expression to an equivalent fully parenthesized infix expression.
     *
     * Assumptions:
     * <ul>
     *     <li>Operands are single alphabetic characters</li>
     *     <li>Operators are those recognized by {@link #isOperator(char)}</li>
     * </ul>
     *
     * @param expression the postfix expression to convert
     * @return the equivalent fully parenthesized infix expression,
     *         or an empty string if {@code expression} is {@code null} or empty
     * @throws IllegalArgumentException if the postfix expression is invalid
     */
    public static String postfixToInfix(String expression) {
        if (expression == null || expression.isEmpty()) {
            return "";
        }

        if (!isValidPostfix(expression)) {
            throw new IllegalArgumentException("Invalid Postfix Expression");
        }

        Stack<String> stack = new Stack<>();
        StringBuilder builder = new StringBuilder();

        for (char ch : expression.toCharArray()) {
            if (!isOperator(ch)) {
                stack.push(Character.toString(ch));
            } else {
                String rightOperand = stack.pop();
                String leftOperand = stack.pop();

                builder.append('(')
                       .append(leftOperand)
                       .append(ch)
                       .append(rightOperand)
                       .append(')');

                stack.push(builder.toString());
                builder.setLength(0);
            }
        }

        return stack.pop();
    }
}