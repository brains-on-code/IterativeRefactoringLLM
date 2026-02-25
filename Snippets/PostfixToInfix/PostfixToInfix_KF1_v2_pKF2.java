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
     * @return {@code true} if {@code ch} is one of +, -, /, *, ^; {@code false} otherwise
     */
    public static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '/' || ch == '*' || ch == '^';
    }

    /**
     * Validates whether the given string is a syntactically valid postfix expression.
     * <p>
     * A valid postfix expression must:
     * <ul>
     *     <li>Contain only single-letter operands (alphabetic characters) and supported operators</li>
     *     <li>Have at least 3 characters unless it is a single operand</li>
     *     <li>Have exactly one more operand than operators</li>
     *     <li>Never have more operators than operands at any point when scanned left to right</li>
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
                if (operatorCount >= operandCount) {
                    return false;
                }
            } else if (Character.isAlphabetic(ch)) {
                operandCount++;
            } else {
                // Invalid character
                return false;
            }
        }

        return operandCount == operatorCount + 1;
    }

    /**
     * Converts a postfix expression to its equivalent infix expression.
     * <p>
     * Operands are assumed to be single alphabetic characters. Operators are
     * limited to those recognized by {@link #isOperator(char)}. The resulting infix
     * expression is fully parenthesized.
     *
     * @param expression the postfix expression to convert
     * @return the equivalent fully parenthesized infix expression
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