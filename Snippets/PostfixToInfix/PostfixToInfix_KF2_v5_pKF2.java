package com.thealgorithms.stacks;

import java.util.Stack;

public final class PostfixToInfix {

    private PostfixToInfix() {
        // Utility class; prevent instantiation
    }

    private static final String OPERATORS = "+-/*^";

    /**
     * Checks if the given character is a supported operator.
     *
     * @param token the character to check
     * @return true if the character is an operator, false otherwise
     */
    public static boolean isOperator(char token) {
        return OPERATORS.indexOf(token) >= 0;
    }

    /**
     * Validates whether the given string is a syntactically valid postfix expression.
     * <p>
     * Rules:
     * <ul>
     *     <li>Must not be null or empty</li>
     *     <li>A single alphabetic character is considered valid</li>
     *     <li>Length must be at least 3 for multi-token expressions</li>
     *     <li>For a valid postfix expression, the number of operands must be exactly
     *     one more than the number of operators</li>
     *     <li>At any point while scanning from left to right, the number of operators
     *     must be strictly less than the number of operands</li>
     * </ul>
     *
     * @param postfix the postfix expression to validate
     * @return true if the expression is valid, false otherwise
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
                // At any point, operators cannot be equal to or exceed operands
                if (operatorCount >= operandCount) {
                    return false;
                }
            } else {
                operandCount++;
            }
        }

        // For a valid postfix expression: operands = operators + 1
        return operandCount == operatorCount + 1;
    }

    /**
     * Converts a valid postfix expression to its equivalent infix expression.
     * <p>
     * Example: {@code "ab+c*"} becomes {@code "((a+b)*c)"}.
     *
     * @param postfix the postfix expression to convert
     * @return the corresponding infix expression, or an empty string if input is null or empty
     * @throws IllegalArgumentException if the postfix expression is invalid
     */
    public static String getPostfixToInfix(String postfix) {
        if (postfix == null || postfix.isEmpty()) {
            return "";
        }

        if (!isValidPostfixExpression(postfix)) {
            throw new IllegalArgumentException("Invalid Postfix Expression");
        }

        Stack<String> stack = new Stack<>();
        StringBuilder builder = new StringBuilder();

        for (char token : postfix.toCharArray()) {
            if (!isOperator(token)) {
                // Token is an operand; push it directly onto the stack
                stack.push(Character.toString(token));
                continue;
            }

            // Token is an operator; pop two operands from the stack
            String rightOperand = stack.pop();
            String leftOperand = stack.pop();

            // Form a new infix sub-expression: (left operator right)
            builder.append('(')
                   .append(leftOperand)
                   .append(token)
                   .append(rightOperand)
                   .append(')');

            // Push the newly formed sub-expression back onto the stack
            stack.push(builder.toString());
            builder.setLength(0);
        }

        // Final element on the stack is the complete infix expression
        return stack.pop();
    }
}