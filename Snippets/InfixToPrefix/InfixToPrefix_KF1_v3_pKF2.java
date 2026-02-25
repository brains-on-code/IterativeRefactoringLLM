package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
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

        StringBuilder output = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        String reversedExpression = new StringBuilder(expression).reverse().toString();

        for (char ch : reversedExpression.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                output.append(ch);
            } else if (ch == ')') {
                operatorStack.push(ch);
            } else if (ch == '(') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != ')') {
                    output.append(operatorStack.pop());
                }
                if (!operatorStack.isEmpty()) {
                    operatorStack.pop();
                }
            } else {
                while (!operatorStack.isEmpty()
                        && precedence(ch) < precedence(operatorStack.peek())) {
                    output.append(operatorStack.pop());
                }
                operatorStack.push(ch);
            }
        }

        while (!operatorStack.isEmpty()) {
            output.append(operatorStack.pop());
        }

        return output.reverse().toString();
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