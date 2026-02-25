package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for converting postfix expressions to fully parenthesized infix expressions.
 *
 * <p>Example:
 * <pre>
 *     PostfixToInfix.getPostfixToInfix("ab+c*")  // returns "((a+b)*c)"
 * </pre>
 */
public final class PostfixToInfix {

    private static final String OPERATORS = "+-/*^";

    private PostfixToInfix() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns {@code true} if the given character is a supported arithmetic operator.
     *
     * @param token the character to check
     */
    public static boolean isOperator(char token) {
        return OPERATORS.indexOf(token) >= 0;
    }

    /**
     * Returns {@code true} if the given string is a syntactically valid postfix expression.
     *
     * <p>Validation rules:
     * <ul>
     *   <li>A single alphabetic character is valid.</li>
     *   <li>Otherwise, the expression must contain at least two operands and one operator.</li>
     *   <li>Scanning left to right, the number of operands must always be greater
     *       than the number of operators.</li>
     *   <li>At the end, {@code operandCount == operatorCount + 1} must hold.</li>
     * </ul>
     *
     * @param postfix the postfix expression to validate
     */
    public static boolean isValidPostfixExpression(String postfix) {
        if (postfix == null || postfix.isEmpty()) {
            return false;
        }

        if (postfix.length() == 1 && Character.isAlphabetic(postfix.charAt(0))) {
            return true;
        }

        if (postfix.length() < 3) {
            return false;
        }

        int operandCount = 0;
        int operatorCount = 0;

        for (char token : postfix.toCharArray()) {
            if (isOperator(token)) {
                operatorCount++;
                if (operatorCount >= operandCount) {
                    return false;
                }
            } else {
                operandCount++;
            }
        }

        return operandCount == operatorCount + 1;
    }

    /**
     * Converts a valid postfix expression to its equivalent fully parenthesized infix form.
     *
     * <p>Example:
     * <pre>
     *     "ab+c*" -> "((a+b)*c)"
     * </pre>
     *
     * @param postfix the postfix expression to convert
     * @return the equivalent infix expression, or an empty string if {@code postfix} is null or empty
     * @throws IllegalArgumentException if {@code postfix} is not a valid postfix expression
     */
    public static String getPostfixToInfix(String postfix) {
        if (postfix == null || postfix.isEmpty()) {
            return "";
        }

        if (!isValidPostfixExpression(postfix)) {
            throw new IllegalArgumentException("Invalid Postfix Expression");
        }

        Stack<String> stack = new Stack<>();

        for (char token : postfix.toCharArray()) {
            if (!isOperator(token)) {
                stack.push(Character.toString(token));
            } else {
                String rightOperand = stack.pop();
                String leftOperand = stack.pop();
                String expression = "(" + leftOperand + token + rightOperand + ")";
                stack.push(expression);
            }
        }

        return stack.pop();
    }
}