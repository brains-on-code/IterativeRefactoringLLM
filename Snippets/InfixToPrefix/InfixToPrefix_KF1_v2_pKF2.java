package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Converts an infix expression to a prefix expression.
     *
     * @param expression the infix expression
     * @return the corresponding prefix expression
     * @throws IllegalArgumentException if the expression has unbalanced brackets
     * @throws NullPointerException     if the input expression is null
     */
    public static String method1(String expression) {
        if (expression == null) {
            throw new NullPointerException("Input expression cannot be null.");
        }

        expression = expression.trim();
        if (expression.isEmpty()) {
            return "";
        }

        if (!BalancedBrackets.isBalanced(extractBrackets(expression))) {
            throw new IllegalArgumentException("Invalid expression: unbalanced brackets.");
        }

        StringBuilder result = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        String reversedExpression = new StringBuilder(expression).reverse().toString();

        for (char ch : reversedExpression.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                result.append(ch);
            } else if (ch == ')') {
                operators.push(ch);
            } else if (ch == '(') {
                while (!operators.isEmpty() && operators.peek() != ')') {
                    result.append(operators.pop());
                }
                if (!operators.isEmpty()) {
                    operators.pop();
                }
            } else {
                while (!operators.isEmpty() && precedence(ch) < precedence(operators.peek())) {
                    result.append(operators.pop());
                }
                operators.push(ch);
            }
        }

        while (!operators.isEmpty()) {
            result.append(operators.pop());
        }

        return result.reverse().toString();
    }

    /**
     * Returns the precedence of an operator.
     *
     * @param operator the operator character
     * @return precedence value (higher means higher precedence), or -1 if not an operator
     */
    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 0;
            case '*':
            case '/':
                return 1;
            case '^':
                return 2;
            default:
                return -1;
        }
    }

    /**
     * Extracts only bracket characters from the given expression.
     *
     * @param expression the input expression
     * @return a string containing only bracket characters
     */
    private static String extractBrackets(String expression) {
        Pattern nonBracketPattern = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher matcher = nonBracketPattern.matcher(expression);
        return matcher.replaceAll("");
    }
}