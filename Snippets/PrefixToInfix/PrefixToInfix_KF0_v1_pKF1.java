package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Converts a prefix expression to an infix expression using a stack.
 *
 * The input prefix expression should consist of
 * valid operands (letters or digits) and operators (+, -, *, /, ^).
 * Parentheses are not required in the prefix string.
 */
public final class PrefixToInfix {
    private PrefixToInfix() {
    }

    /**
     * Determines if a given character is a valid arithmetic operator.
     *
     * @param character the character to check
     * @return true if the character is an operator, false otherwise
     */
    public static boolean isOperator(char character) {
        return character == '+'
            || character == '-'
            || character == '/'
            || character == '*'
            || character == '^';
    }

    /**
     * Converts a valid prefix expression to an infix expression.
     *
     * @param prefixExpression the prefix expression to convert
     * @return the equivalent infix expression
     * @throws NullPointerException if the prefix expression is null
     */
    public static String convertPrefixToInfix(String prefixExpression) {
        if (prefixExpression == null) {
            throw new NullPointerException("Null prefix expression");
        }
        if (prefixExpression.isEmpty()) {
            return "";
        }

        Stack<String> expressionStack = new Stack<>();

        // Iterate over the prefix expression from right to left
        for (int index = prefixExpression.length() - 1; index >= 0; index--) {
            char currentCharacter = prefixExpression.charAt(index);

            if (isOperator(currentCharacter)) {
                // Pop two operands from stack
                String leftOperand = expressionStack.pop();
                String rightOperand = expressionStack.pop();

                // Form the infix expression with parentheses
                String infixExpression = "(" + leftOperand + currentCharacter + rightOperand + ")";

                // Push the resulting infix expression back onto the stack
                expressionStack.push(infixExpression);
            } else {
                // Push operand onto stack
                expressionStack.push(Character.toString(currentCharacter));
            }
        }

        if (expressionStack.size() != 1) {
            throw new ArithmeticException("Malformed prefix expression");
        }

        // Final element on the stack is the full infix expression
        return expressionStack.pop();
    }
}