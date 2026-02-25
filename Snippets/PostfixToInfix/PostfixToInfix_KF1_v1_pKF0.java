package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for working with postfix expressions.
 */
public final class PostfixUtils {

    private PostfixUtils() {
        // Prevent instantiation
    }

    /**
     * Checks whether the given character is a supported operator.
     *
     * @param ch the character to check
     * @return {@code true} if the character is an operator, {@code false} otherwise
     */
    public static boolean isOperator(char ch) {
        return ch == '+'
            || ch == '-'
            || ch == '/'
            || ch == '*'
            || ch == '^';
    }

    /**
     * Validates whether the given string is a syntactically valid postfix expression.
     * Operands are assumed to be single alphabetic characters.
     *
     * @param expression the postfix expression to validate
     * @return {@code true} if the expression is valid, {@code false} otherwise
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
                // At any point, operators cannot be equal to or exceed operands
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
     * Converts a postfix expression to its equivalent infix expression.
     * Operands are assumed to be single alphabetic characters.
     *
     * @param postfixExpression the postfix expression to convert
     * @return the corresponding infix expression
     * @throws IllegalArgumentException if the postfix expression is invalid
     */
    public static String postfixToInfix(String postfixExpression) {
        if (postfixExpression == null || postfixExpression.isEmpty()) {
            return "";
        }

        if (!isValidPostfix(postfixExpression)) {
            throw new IllegalArgumentException("Invalid Postfix Expression");
        }

        Stack<String> stack = new Stack<>();
        StringBuilder builder = new StringBuilder();

        for (char ch : postfixExpression.toCharArray()) {
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