package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for converting prefix expressions to infix expressions.
 */
public final class PrefixToInfixConverter {

    private PrefixToInfixConverter() {
    }

    /**
     * Checks whether the given character is a supported operator.
     *
     * @param character the character to check
     * @return true if the character is an operator; false otherwise
     */
    public static boolean isOperator(char character) {
        return character == '+'
            || character == '-'
            || character == '/'
            || character == '*'
            || character == '^';
    }

    /**
     * Converts a prefix expression to its corresponding infix expression.
     *
     * @param prefixExpression the prefix expression to convert
     * @return the resulting infix expression
     * @throws NullPointerException if the prefix expression is null
     * @throws ArithmeticException  if the prefix expression is malformed
     */
    public static String convertPrefixToInfix(String prefixExpression) {
        if (prefixExpression == null) {
            throw new NullPointerException("Prefix expression must not be null");
        }
        if (prefixExpression.isEmpty()) {
            return "";
        }

        Stack<String> operandStack = new Stack<>();

        for (int index = prefixExpression.length() - 1; index >= 0; index--) {
            char currentCharacter = prefixExpression.charAt(index);

            if (isOperator(currentCharacter)) {
                String leftOperand = operandStack.pop();
                String rightOperand = operandStack.pop();
                String infixSubexpression =
                    "(" + leftOperand + currentCharacter + rightOperand + ")";
                operandStack.push(infixSubexpression);
            } else {
                operandStack.push(Character.toString(currentCharacter));
            }
        }

        if (operandStack.size() != 1) {
            throw new ArithmeticException("Malformed prefix expression");
        }

        return operandStack.pop();
    }
}