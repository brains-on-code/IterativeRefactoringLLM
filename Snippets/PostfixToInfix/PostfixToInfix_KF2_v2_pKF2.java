package com.thealgorithms.stacks;

import java.util.Stack;

public final class PostfixToInfix {

    private PostfixToInfix() {
        // Prevent instantiation
    }

    /**
     * Returns {@code true} if the given character is a supported operator.
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
     * Returns {@code true} if the given string is a syntactically valid postfix expression.
     * <p>Validation rules:
     * <ul>
     *     <li>A single alphabetic character is valid (single operand).</li>
     *     <li>For longer expressions, the number of operands must be exactly one more
     *         than the number of operators.</li>
     *     <li>While scanning left to right, the number of operators must always be
     *         strictly less than the number of operands.</li>
     * </ul>
     *
     * @param postfix the postfix expression to validate
     * @return {@code true} if the expression is valid; {@code false} otherwise
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
                // At any point, operators must be fewer than operands
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
     * Converts a postfix expression to its equivalent infix expression.
     * <p>Example:
     * <pre>{@code
     * AB+C*  ->  ((A+B)*C)
     * }</pre>
     *
     * @param postfix the postfix expression to convert
     * @return the corresponding infix expression, or an empty string if the input is null or empty
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
                stack.push(Character.toString(token));
            } else {
                String operandB = stack.pop();
                String operandA = stack.pop();

                builder.append('(')
                       .append(operandA)
                       .append(token)
                       .append(operandB)
                       .append(')');

                stack.push(builder.toString());
                builder.setLength(0);
            }
        }

        return stack.pop();
    }
}