package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for working with postfix expressions.
 */
public final class PostfixUtils {

    private static final String OPERATORS = "+-/*^";

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
        return OPERATORS.indexOf(ch) >= 0;
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

        int length = expression.length();

        if (length == 1) {
            return Character.isAlphabetic(expression.charAt(0));
        }

        if (length < 3) {
            return false;
        }

        int operandCount = 0;
        int operatorCount = 0;

        for (char ch : expression.toCharArray()) {
            if (isOperator(ch)) {
                operatorCount++;
                if (operatorCount >= operandCount) {
                    return false;
                }
            } else if (Character.isAlphabetic(ch)) {
                operandCount++;
            } else {
                return false;
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

        Stack<String> operands = new Stack<>();

        for (char ch : postfixExpression.toCharArray()) {
            if (isOperator(ch)) {
                String rightOperand = operands.pop();
                String leftOperand = operands.pop();
                String subExpression = "(" + leftOperand + ch + rightOperand + ")";
                operands.push(subExpression);
            } else {
                operands.push(Character.toString(ch));
            }
        }

        return operands.pop();
    }
}