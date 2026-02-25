package com.thealgorithms.stacks;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Utility class for converting prefix expressions to infix.
 */
public final class PrefixToInfixConverter {

    private static final String NULL_EXPRESSION_MESSAGE = "Null prefix expression";
    private static final String MALFORMED_EXPRESSION_MESSAGE = "Malformed prefix expression";

    private PrefixToInfixConverter() {
        // Prevent instantiation
    }

    /**
     * Checks whether the given character is a supported operator.
     *
     * @param ch the character to check
     * @return true if the character is an operator; false otherwise
     */
    public static boolean isOperator(char ch) {
        return ch == '+'
            || ch == '-'
            || ch == '/'
            || ch == '*'
            || ch == '^';
    }

    /**
     * Converts a prefix expression to its infix representation.
     *
     * @param prefixExpression the prefix expression to convert
     * @return the infix representation of the given prefix expression
     * @throws NullPointerException if {@code prefixExpression} is null
     * @throws ArithmeticException  if the prefix expression is malformed
     */
    public static String convertPrefixToInfix(String prefixExpression) {
        validatePrefixExpression(prefixExpression);

        if (prefixExpression.isEmpty()) {
            return "";
        }

        Deque<String> operandStack = new ArrayDeque<>();

        for (int index = prefixExpression.length() - 1; index >= 0; index--) {
            char currentChar = prefixExpression.charAt(index);

            if (isOperator(currentChar)) {
                pushCombinedExpression(operandStack, currentChar);
            } else {
                operandStack.push(String.valueOf(currentChar));
            }
        }

        if (operandStack.size() != 1) {
            throw new ArithmeticException(MALFORMED_EXPRESSION_MESSAGE);
        }

        return operandStack.pop();
    }

    private static void validatePrefixExpression(String prefixExpression) {
        if (prefixExpression == null) {
            throw new NullPointerException(NULL_EXPRESSION_MESSAGE);
        }
    }

    private static void pushCombinedExpression(Deque<String> operandStack, char operator) {
        if (operandStack.size() < 2) {
            throw new ArithmeticException(MALFORMED_EXPRESSION_MESSAGE);
        }

        String leftOperand = operandStack.pop();
        String rightOperand = operandStack.pop();
        operandStack.push("(" + leftOperand + operator + rightOperand + ")");
    }
}